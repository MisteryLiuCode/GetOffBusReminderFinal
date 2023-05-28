package com.liu.getOffBusReminderFinal.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liu.getOffBusReminderFinal.common.SystemException;
import com.liu.getOffBusReminderFinal.constant.LocationSort;
import com.liu.getOffBusReminderFinal.dao.LocationMapper;
import com.liu.getOffBusReminderFinal.dao.UserInfoMapper;
import com.liu.getOffBusReminderFinal.dao.entity.LocationInfoDO;
import com.liu.getOffBusReminderFinal.dao.entity.UserInfoDO;
import com.liu.getOffBusReminderFinal.entity.req.*;
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
import java.util.*;

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

    public Boolean editLocation(EditLocationReq locationReq) {
        log.info("编辑位置入参:{}", locationReq);
        //查询位置是否存在
        LocationInfoDO locationInfoDO = locationMapper.queryById(locationReq.getLocationId());
        if (StringUtils.isEmpty(locationInfoDO)) {
            throw new SystemException("要修改的位置信息不存在");
        }
        //保存地点位置
        locationInfoDO.setLocationDesCn(locationReq.getLocationDesCn());
        locationInfoDO.setLocationDes(locationReq.getLocationDes());
        locationInfoDO.setSubwayLine(locationReq.getSubwayLine());
        locationInfoDO.setSort(locationReq.getSort());
        locationInfoDO.setUpdateTime(new Date());
        locationInfoDO.setYn(1);
        return locationMapper.update(locationInfoDO) == 1 ? true : false;
    }


    public Boolean getDistance(DistanceReq req) {
        log.info("获取直线距离入参:{}", JSONObject.toJSONString(req));
        if (!getOffBusHelper.isWorkDay()){
            log.info("今天非工作日");
           return false;
        }
        Configuration weatherConfig = ConfigUtil.getOffBusReminderConfig();
        //起始经纬度
        String startPoint = req.getOriLong() + "," + req.getOriLat();
        //通过 userId 查询该用户的所有位置
        List<LocationInfoDO> locationInfoDOS = locationMapper.queryByUserId(req.getUserId());
        for (LocationInfoDO locationInfoDO : locationInfoDOS) {
            //判断现在时间是上午还是下午，true为上午，false为下午
            Boolean isAm = getOffBusHelper.des();
            if (isAm && locationInfoDO.getSort() == 1 || !isAm && locationInfoDOS.get(locationInfoDOS.size() - 1).getSort()
                    .equals(locationInfoDO.getSort())) {
                continue;
            }
            //获取目的地经纬度信息
            String locationKey = locationInfoDO.getLocationId() + LocationSort.message;
            String alwLocationKey = locationInfoDO.getLocationId() + LocationSort.alwMessage;
            String endPoint = locationInfoDO.getLocationDes();
            Double distance = getOffBusHelper.getDistance(startPoint, endPoint, weatherConfig);
            log.info("计算顺序为{}，距离为{}",locationInfoDO.getSort(),distance);
            if (distance < 2000 && !globalCache.hasKey(locationKey)) {
                String url = "https://api.day.app/rGFEQo4A9yvep2fHcxqEne/马上到站了";//指定URL
                String result = HttpUtil.createGet(url).execute().body();
                log.info("发送通知结果：{}", result);
                globalCache.set(locationKey, true, 1800);
            }
            if (distance < 800 && !globalCache.hasKey(alwLocationKey)) {
                String url = "https://api.day.app/rGFEQo4A9yvep2fHcxqEne/即将下车了!";//指定URL
                for (int i = 0; i < 3; i++) {
                    String result = HttpUtil.createGet(url).execute().body();
                    log.info("连续发送通知结果：{}", result);
                }
                globalCache.set(alwLocationKey, true, 1800);
            }
            log.info("距离为：{}", distance);
        }
        return true;
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
