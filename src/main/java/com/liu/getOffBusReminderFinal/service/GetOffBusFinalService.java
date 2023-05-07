package com.liu.getOffBusReminderFinal.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liu.getOffBusReminderFinal.common.SystemException;
import com.liu.getOffBusReminderFinal.dao.LocationMapper;
import com.liu.getOffBusReminderFinal.dao.UserInfoMapper;
import com.liu.getOffBusReminderFinal.dao.entity.LocationInfoDO;
import com.liu.getOffBusReminderFinal.dao.entity.UserInfoDO;
import com.liu.getOffBusReminderFinal.entity.req.DistanceReq;
import com.liu.getOffBusReminderFinal.entity.req.LocationReq;
import com.liu.getOffBusReminderFinal.entity.req.UserReq;
import com.liu.getOffBusReminderFinal.entity.req.WorkAndHomeLocationReq;
import com.liu.getOffBusReminderFinal.entity.resp.AllDistance;
import com.liu.getOffBusReminderFinal.enums.LocationEnum;
import com.liu.getOffBusReminderFinal.helper.GetOffBusHelper;
import com.liu.getOffBusReminderFinal.utils.ConfigUtil;
import com.liu.getOffBusReminderFinal.utils.IGlobalCache;
import com.liu.getOffBusReminderFinal.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liushuaibiao
 * @date 2023/4/17 14:21
 */
@Service
@Slf4j
public class GetOffBusFinalService {

    @Resource
    private GetOffBusHelper getOffBusHelper;

    @Autowired
    private IGlobalCache globalCache;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private LocationMapper locationMapper;

    public Boolean saveUser(UserReq userReq) {
        log.info("保存用户:{}", JSONObject.toJSONString(userReq));
        UserInfoDO userInfoDO = userInfoMapper.queryByUserId(userReq.getUserId());
        if (userInfoDO == null) {
            userInfoDO = new UserInfoDO();
            userInfoDO.setUserId(userReq.getUserId());
            userInfoDO.setYn(1);
//            String time = getOffBusHelper.getTime();
            userInfoDO.setAddTime(new Date());
            log.info("用户入库开始");
            int insert = userInfoMapper.insert(userInfoDO);
            return insert == 1 ? true : false;
        }
        return true;
    }

    public String saveLocation(LocationReq locationReq) {
        log.info("保存位置入参:{}", locationReq);
        //查询用户是否存在
        UserInfoDO userInfoDO = userInfoMapper.queryByUserId(locationReq.getUserId());
        if (StringUtils.isEmpty(userInfoDO)) {
            throw new SystemException("用户不存在");
        }
        //保存地点位置
        LocationInfoDO locationInfoDO = new LocationInfoDO();
        locationInfoDO.setLocationId(IdUtils.snowflakeId());
        locationInfoDO.setUserId(locationReq.getUserId());
        locationInfoDO.setLocationDesCn(locationReq.getLocationDesCn());
        locationInfoDO.setLocationDes(locationReq.getLocationDes());
        locationInfoDO.setSubwayLine(locationReq.getSubwayLine());
        locationInfoDO.setSort(locationReq.getSort());
        locationInfoDO.setAddTime(new Date());
        locationInfoDO.setYn(1);
        locationMapper.insert(locationInfoDO);
        return locationInfoDO.getLocationId();
    }


    public List<AllDistance> getAllDistance(DistanceReq req) {
        log.info("获取全部距离入参:{}", JSONObject.toJSONString(req));
        //查询此用户id下面的所有位置
        Configuration weatherConfig = ConfigUtil.getOffBusReminderConfig();
        List<LocationInfoDO> locationInfoDOList = locationMapper.queryByUserId(req.getUserId());
        List<AllDistance> resAllDistance = new ArrayList<>();
        for (LocationInfoDO locationInfoDO : locationInfoDOList) {
            AllDistance allDistance = new AllDistance();
            //起始经纬度
            String startPoint = req.getOriLong() + "," + req.getOriLat();
            //获取目的地经纬度
//            String endPoint = getOffBusHelper.getEndPoint(req.getUserId());
            String endPoint = locationInfoDO.getLocationDes();
            if (endPoint.equals("")) {
                allDistance.setDistance((double) 0);
                allDistance.setLocationId(locationInfoDO.getLocationId());
                resAllDistance.add(allDistance);
                continue;
            }
            Double distance = getOffBusHelper.getDistance(startPoint, endPoint, weatherConfig);
            allDistance.setLocationId(locationInfoDO.getLocationId());
            allDistance.setDistance(distance);
            if (distance < 2000 && !globalCache.hasKey(locationInfoDO.getLocationId())) {
                String url = "https://api.day.app/DMNK5oTh5FV3RvwpxKvxwB/马上到站了";//指定URL
                String result = HttpUtil.createGet(url).execute().body();
                log.info("发送通知结果：{}", result);
                globalCache.set(locationInfoDO.getLocationId(), true, 1800);
            }
            log.info("距离为：{}", distance);
        }
        return resAllDistance;
    }


    public Double getDistance(DistanceReq req) {
        log.info("获取直线距离入参:{}", JSONObject.toJSONString(req));
        Configuration weatherConfig = ConfigUtil.getOffBusReminderConfig();
        //起始经纬度
        String startPoint = req.getOriLong() + "," + req.getOriLat();
        //获取目的地经纬度
        String endPoint = getOffBusHelper.getEndPoint(req.getUserId());
        if (endPoint.equals("")) {
            return Double.valueOf(0);
        }
        Double distance = getOffBusHelper.getDistance(startPoint, endPoint, weatherConfig);
        if (distance < 2000 && !globalCache.hasKey("sendMessage")) {
            String url = "https://api.day.app/DMNK5oTh5FV3RvwpxKvxwB/马上到站了";//指定URL
            String result = HttpUtil.createGet(url).execute().body();
            log.info("发送通知结果：{}", result);
            globalCache.set("sendMessage", true, 1800);
        }
        log.info("距离为：{}", distance);
        return distance;
    }

    public String getDes(UserReq req) {
        log.info("获取中文目的地入参:{}", JSONObject.toJSONString(req));
        //判断现在时间是上午还是下午，true为上午，false为下午
        Boolean isAm = getOffBusHelper.des();
        Configuration weatherConfig = ConfigUtil.getOffBusReminderConfig();
        String des = "";
        if (isAm) {
            //获取上班目的地，中文
            des = weatherConfig.getString("upDes");
        } else {
            //获取下班目的，中文
            des = weatherConfig.getString("downDes");
        }
        log.info("获取目的地为{}", des);
        return des;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer getLocation(WorkAndHomeLocationReq req) {
        log.info("保存上下班入参:{}", JSONObject.toJSONString(req));
        String point = req.getOriLong() + "," + req.getOriLat();
        try {
            UserInfoDO userInfoDO = new UserInfoDO();
            String time = getOffBusHelper.getTime();
            //如果为上班位置
            if (req.getLocationType().equals(LocationEnum.TYPE_1.getCode())) {
//                userInfoDO.setWorkDes(point);
                userInfoDO.setUserId(req.getUserId());
                userInfoDO.setUpdateTime(new Date());
                return userInfoMapper.update(userInfoDO);
            } else {
//                userInfoDO.setHomeDes(point);
                userInfoDO.setUserId(req.getUserId());
                userInfoDO.setUpdateTime(new Date());
                return userInfoMapper.update(userInfoDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getWorkAndHomeLocation(LocationReq req) {
        log.info("获取上下班位置信息入参:{}", JSONObject.toJSONString(req));
        UserInfoDO userInfoDO = userInfoMapper.queryByUserId(req.getUserId());
        try {
            //如果为上班位置
////            if (req.getLocationType().equals(LocationEnum.TYPE_1.getCode())) {
////                String workDes = userInfoDO.getWorkDes();
////                log.info("获取的上班位置{}",workDes);
////                return workDes;
//
////            } else {
////                String homeDes = userInfoDO.getHomeDes();
////                log.info("获取的下班位置{}",homeDes);
////                return homeDes;
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    public List<String> inputPrompt(String oriLong, String oriLat, String keywords) {
        log.info("输入提示入参:{},{},{},{}", oriLong, oriLat, keywords);
        Configuration weatherConfig = ConfigUtil.getOffBusReminderConfig();
        //我的位置经纬度
        String myPoint = oriLong + "," + oriLat;
        List<String> nameList = getOffBusHelper.getInputPrompt(myPoint, keywords, weatherConfig);
        log.info("获取输入提示结果:{}", JSON.toJSONString(nameList));
        return nameList;
    }
}
