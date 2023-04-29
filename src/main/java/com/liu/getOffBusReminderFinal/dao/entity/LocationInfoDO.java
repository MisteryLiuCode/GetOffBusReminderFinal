package com.liu.getOffBusReminderFinal.dao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LocationInfoDO {
    private static final long serialVersionUID = -91969758749726312L;

    private String locationId;

    private String  userId;

    private String locationDesCn;

    private String locationDes;

    private Date addTime;

    private Date updateTime;

    private int yn;

}
