package com.liu.getOffBusReminderFinal.enums;

/**
 * @author liushuaibiao
 * @date 2023/4/23 11:16
 */
public enum DefaultResponseErrorType {
    SUCCESS("0000", "操作成功"),
    ERROR("9999", "系统异常,请稍后重试!");

    private final String code;
    private final String message;

    private DefaultResponseErrorType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
