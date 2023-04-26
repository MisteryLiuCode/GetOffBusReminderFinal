package com.liu.getOffBusReminderFinal.controller;


import com.liu.getOffBusReminderFinal.annotation.PrintLog;
import com.liu.getOffBusReminderFinal.common.RespResult;
import com.liu.getOffBusReminderFinal.entity.req.DistanceReq;
import com.liu.getOffBusReminderFinal.entity.req.LocationReq;
import com.liu.getOffBusReminderFinal.entity.req.UserReq;
import com.liu.getOffBusReminderFinal.entity.req.WorkAndHomeLocationReq;
import com.liu.getOffBusReminderFinal.service.GetOffBusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


@RestController
@Slf4j
public class GetOffBusController {

    @Resource
    private GetOffBusService getOffBusService;


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
        return new RespResult<>(getOffBusService.saveUser(userReq));
    }

    /**
     * 获取直线距离
     */
    @RequestMapping("/getDistance")
    public RespResult<Double> getDistance(DistanceReq req) {
        return new RespResult<>(getOffBusService.getDistance(req));
    }

    /**
     * 读取目的地，中文
     * @return
     */
    @RequestMapping("/getDestination")
    public RespResult<String> getDes(UserReq req) {
        return new RespResult<>(getOffBusService.getDes(req));
    }

    /**
     * 保存上下班位置信息
     */
    @RequestMapping("/saveLocation")
    public RespResult<Integer> saveLocation(WorkAndHomeLocationReq req) {
        return new RespResult<>(getOffBusService.getLocation(req));
    }

    /**
     * 获取位置信息
     * @return
     */
    @RequestMapping("/getWorkAndHomeLocation")
    @PrintLog
    public String getWorkAndHomeLocation(LocationReq req){
        return getOffBusService.getWorkAndHomeLocation(req);
    }

    /**
     * 输入提示
     */
    @RequestMapping("/inputPrompt/{oriLong}/{oriLat}/{keywords}")
    public RespResult<List<String>> inputPrompt(@PathVariable String oriLong,
                                                @PathVariable String oriLat,
                                                @PathVariable String keywords){
        return new RespResult<>(getOffBusService.inputPrompt(oriLong, oriLat, keywords));
    }
}
