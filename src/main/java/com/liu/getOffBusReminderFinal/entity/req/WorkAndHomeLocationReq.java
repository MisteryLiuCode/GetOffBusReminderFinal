package com.liu.getOffBusReminderFinal.entity.req;

import lombok.Data;

/**
 * @author liushuaibiao
 * @date 2023/4/25 14:10
 */
@Data
public class WorkAndHomeLocationReq {

    private String oriLong;

    private String oriLat;

    private String locationType;

    private String userId;


}
