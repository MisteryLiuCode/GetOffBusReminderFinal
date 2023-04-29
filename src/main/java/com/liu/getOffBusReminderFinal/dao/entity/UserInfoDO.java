package com.liu.getOffBusReminderFinal.dao.entity;

import lombok.Data;

import java.util.Date;

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
     * 添加时间
     */
    private Date addTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否有效  (0否 1是)
     */
    private Integer yn;
}
