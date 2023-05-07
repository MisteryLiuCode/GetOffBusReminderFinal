package com.liu.getOffBusReminderFinal.controller;

import com.liu.getOffBusReminderFinal.common.RespResult;
import com.liu.getOffBusReminderFinal.entity.req.DistanceReq;
import com.liu.getOffBusReminderFinal.entity.req.LocationReq;
import com.liu.getOffBusReminderFinal.entity.req.UserReq;
import com.liu.getOffBusReminderFinal.entity.req.WorkAndHomeLocationReq;
import com.liu.getOffBusReminderFinal.entity.resp.AllDistance;
import com.liu.getOffBusReminderFinal.service.GetOffBusFinalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@Slf4j
public class GetOffBusFinalController {

    @Resource
    private GetOffBusFinalService getOffBusFinalService;


    @RequestMapping("/test")
    public RespResult<String> test(){
        return new RespResult<>("success");
    }

    /**
     * 保存用户,第一次打开的时候保存用户基本信息
     * @return
     */
    @RequestMapping("/saveUserInfo")
    public RespResult<Boolean> saveUserInfo(UserReq userReq) {
        return new RespResult<>(getOffBusFinalService.saveUser(userReq));
    }

    /**
     * 保存地点信息
     */
    @RequestMapping("/saveLocation")
    public RespResult<String> saveLocation(LocationReq locationReq){
        return new RespResult<>(getOffBusFinalService.saveLocation(locationReq));

    }
    /**
     * 批量计算距离
     */
    @RequestMapping("/getAllDistance")
    public RespResult<List<AllDistance>> getAllDistance(DistanceReq req) {
        return new RespResult<>(getOffBusFinalService.getAllDistance(req));
    }



    /**
     * 编辑地点信息
     */
//    @RequestMapping("/ editLocation")
//    public RespResult<Integer> editLocation(LocationReq locationReq){
//        return new RespResult<>(getOffBusFinalService.editLocation(locationReq));
//    }


    /**
     * 获取直线距离
     */
    @RequestMapping("/getDistance")
    public RespResult<Double> getDistance(DistanceReq req) {
        return new RespResult<>(getOffBusFinalService.getDistance(req));
    }

    /**
     * 读取目的地，中文
     * @return
     */
    @RequestMapping("/getDestination")
    public RespResult<String> getDes(UserReq req) {
        return new RespResult<>(getOffBusFinalService.getDes(req));
    }

    /**
     * 保存上下班位置信息
     */
//    @RequestMapping("/saveLocation")
//    public RespResult<Integer> saveLocation(WorkAndHomeLocationReq req) {
//        return new RespResult<>(getOffBusFinalService.getLocation(req));
//    }

    /**
     * 获取位置信息
     * @return
     */
    @RequestMapping("/getWorkAndHomeLocation")
    public String getWorkAndHomeLocation(LocationReq req){
        return getOffBusFinalService.getWorkAndHomeLocation(req);
    }

    /**
     * 输入提示
     */
    @RequestMapping("/inputPrompt/{oriLong}/{oriLat}/{keywords}")
    public RespResult<List<String>> inputPrompt(@PathVariable String oriLong,
                                                @PathVariable String oriLat,
                                                @PathVariable String keywords){
        return new RespResult<>(getOffBusFinalService.inputPrompt(oriLong, oriLat, keywords));
    }




}
