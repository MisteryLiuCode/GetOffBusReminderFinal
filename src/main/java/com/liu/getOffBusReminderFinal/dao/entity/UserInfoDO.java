package com.liu.getOffBusReminderFinal.dao.entity;

import lombok.Data;

/**
 * @author liushuaibiao
 * @date 2023/4/17 18:43
 */
@Data
public class UserInfoDO {

    private static final long serialVersionUID = -91969758749726312L;
    /**
     * 唯一标识用户编号
     */
    private String userId;
    /**
     * 上班中文描述
     */
    private String workDesCn;
    /**
     * 家位置中文描述
     */
    private String homeDesCn;

    /**
     * 上班经纬度信息
     */
    private String workDes;

    /**
     * 家位置经纬度信息
     */
    private String homeDes;

    /**
     * 添加时间
     */
    private String addTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 是否有效  (0否 1是)
     */
    private Integer yn;
}
