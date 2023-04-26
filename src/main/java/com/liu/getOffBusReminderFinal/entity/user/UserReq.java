package com.liu.getOffBusReminderFinal.entity.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author liushuaibiao
 * @date 2023/4/23 13:59
 */
@Data
public class UserReq {

    @NotBlank(message = "用户id不能为空")
    private String userId;
}
