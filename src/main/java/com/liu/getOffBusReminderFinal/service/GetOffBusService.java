package com.liu.getOffBusReminderFinal.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liu.getOffBusReminderFinal.dao.UserInfoMapper;
import com.liu.getOffBusReminderFinal.dao.entity.UserInfoDO;
import com.liu.getOffBusReminderFinal.entity.req.DistanceReq;
import com.liu.getOffBusReminderFinal.entity.req.LocationReq;
import com.liu.getOffBusReminderFinal.entity.req.UserReq;
import com.liu.getOffBusReminderFinal.entity.req.WorkAndHomeLocationReq;
import com.liu.getOffBusReminderFinal.enums.LocationEnum;
import com.liu.getOffBusReminderFinal.helper.GetOffBusHelper;
import com.liu.getOffBusReminderFinal.utils.ConfigUtil;
import com.liu.getOffBusReminderFinal.utils.IGlobalCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liushuaibiao
 * @date 2023/4/17 14:21
 */
@Service
@Slf4j
public class GetOffBusService {

    @Resource
    private GetOffBusHelper getOffBusHelper;

    @Autowired
    private IGlobalCache globalCache;

    @Resource
    private UserInfoMapper userInfoMapper;

    public Boolean saveUser(UserReq userReq){
        log.info("保存用户:{}", JSONObject.toJSONString(userReq));
        UserInfoDO userInfoDO = userInfoMapper.queryByUserId(userReq.getUserId());
        if (userInfoDO==null){
            userInfoDO=new UserInfoDO();
            userInfoDO.setUserId(userReq.getUserId());
            userInfoDO.setYn(1);
            String time = getOffBusHelper.getTime();
            userInfoDO.setAddTime(time);
            log.info("用户入库开始");
            int insert = userInfoMapper.insert(userInfoDO);
            return insert==1?true:false;
        }
        return true;
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
        Double distance = getOffBusHelper.getDistance(startPoint, endPoint,weatherConfig);
        if (distance < 1500 && !globalCache.hasKey("sendMessage")) {
            String url = "https://api.day.app/DMNK5oTh5FV3RvwpxKvxwB/马上到站了";//指定URL
            String result = HttpUtil.createGet(url).execute().body();
            log.info("发送通知结果：{}", result);
            globalCache.set("sendMessage", true, 1800);
        }
        log.info("距离为：{}", distance);
        return distance;
    }

    public String getDes(UserReq req) {
        log.info("获取中文目的地入参:{}",JSONObject.toJSONString(req));
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
        try{
            UserInfoDO userInfoDO = new UserInfoDO();
            String time = getOffBusHelper.getTime();
            //如果为上班位置
            if(req.getLocationType().equals(LocationEnum.TYPE_1.getCode())){
                userInfoDO.setWorkDes(point);
                userInfoDO.setUserId(req.getUserId());
                userInfoDO.setUpdateTime(time);
                return userInfoMapper.update(userInfoDO);
            }else {
                userInfoDO.setHomeDes(point);
                userInfoDO.setUserId(req.getUserId());
                userInfoDO.setUpdateTime(time);
                return userInfoMapper.update(userInfoDO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public String getWorkAndHomeLocation(LocationReq req) {
        log.info("获取上下班位置信息入参:{}", JSONObject.toJSONString(req));
        UserInfoDO userInfoDO = userInfoMapper.queryByUserId(req.getUserId());
        try{
            //如果为上班位置
            if(req.getLocationType().equals(LocationEnum.TYPE_1.getCode())){
                String workDes = userInfoDO.getWorkDes();
                log.info("获取的上班位置{}",workDes);
                return workDes;
            }else {
                String homeDes = userInfoDO.getHomeDes();
                log.info("获取的下班位置{}",homeDes);
                return homeDes;
            }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }


    public List<String> inputPrompt(String oriLong, String oriLat,String keywords) {
        log.info("输入提示入参:{},{},{},{}", oriLong, oriLat,keywords);
        Configuration weatherConfig = ConfigUtil.getOffBusReminderConfig();
        //我的位置经纬度
        String myPoint = oriLong + "," + oriLat;
        List<String> nameList = getOffBusHelper.getInputPrompt(myPoint,keywords,weatherConfig);
        log.info("获取输入提示结果:{}", JSON.toJSONString(nameList));
        return nameList;
    }
}
