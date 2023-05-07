package com.liu.getOffBusReminderFinal.entity.req;

import lombok.Data;

/**
 * @author liushuaibiao
 * @date 2023/4/25 14:33
 */
@Data
public class LocationReq {

    private String userId;

    private String locationDesCn;

    private String locationDes;

    private Integer sort;

    private String subwayLine;
}
