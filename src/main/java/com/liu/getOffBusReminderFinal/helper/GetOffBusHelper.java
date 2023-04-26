package com.liu.getOffBusReminderFinal.helper;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liu.getOffBusReminderFinal.dao.UserInfoMapper;
import com.liu.getOffBusReminderFinal.dao.entity.UserInfoDO;
import com.liu.getOffBusReminderFinal.entity.Root;
import com.liu.getOffBusReminderFinal.entity.Tips;
import org.apache.commons.configuration.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liushuaibiao
 * @date 2023/4/17 14:23
 */
@Component
public class GetOffBusHelper {

    @Resource
    private UserInfoMapper userInfoMapper;


    /**
     * 获取当下时间，true为上午，false为下午
     * @return
     */
    public Boolean des() {
        // 获取当前时间
        LocalTime now = LocalTime.now();

        // 中午12点
        LocalTime am12 = LocalTime.NOON;

        // 比较当前时间和上午12点、下午12点的时间
        boolean isAm = now.isBefore(am12);
        return isAm;
    }

    /**
     * 获取当下位置，根据时间判断，以中午12点为分割线，
     * 上午目的地为上班地铁站，下午目的为下班地铁站
     * @return
     */
    public String getEndPoint(String userId) {
        //判断现在时间是上午还是下午，true为上午，false为下午
        Boolean isAm = des();
        String endPoint = "";
        String s = "";
        UserInfoDO userInfoDO = userInfoMapper.queryByUserId(userId);
        if (isAm) {
            //获取上班目的地经纬度
            endPoint=userInfoDO.getWorkDes()==null?"":userInfoDO.getWorkDes();
        } else {
            //获取下班目的地经纬度
            endPoint=userInfoDO.getHomeDes()==null?"":userInfoDO.getHomeDes();
        }
        return endPoint;
    }


    /**
     * 经纬度算出两点间距离（通过api调用计算）
     */
    public Double getDistance(String startLonLat, String endLonLat, Configuration weatherConfig) {
        //返回起始地startAddr与目的地endAddr之间的距离，单位：米
        Double result = new Double(0);
        String key = weatherConfig.getString("key");
        String distanceUrl = weatherConfig.getString("distanceUrl");
        String queryUrl = distanceUrl+"?key=" + key + "&origins=" + startLonLat + "&destination=" + endLonLat + "&type=0";
        String queryResult = HttpUtil.get(queryUrl);
        JSONObject job = JSONObject.parseObject(queryResult);
        JSONArray ja = job.getJSONArray("results");
        JSONObject jobO = JSONObject.parseObject(ja.getString(0));
        result = Double.parseDouble(jobO.get("distance").toString());
        return result;
    }


    public List<String> getInputPrompt(String myPoint, String keywords, Configuration weatherConfig) {
        //返回起始地startAddr与目的地endAddr之间的距离，单位：米
        String inputPromptUrl = weatherConfig.getString("inputPromptUrl");
        String key = weatherConfig.getString("key");
        String queryUrl = inputPromptUrl+"?key=" + key + "&keywords=" + keywords + "&location=" + myPoint + "&datatype=bus";
        String queryResult = HttpUtil.get(queryUrl);
        Root root = JSONObject.parseObject(queryResult, Root.class);
        List<Tips> tipsList = root.getTips();
        List<String> nameList = tipsList.stream().map(Tips::getName).collect(Collectors.toList());
        return nameList;
    }

    public String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        String format = sdf.format(date);
        return format;
    }
}
