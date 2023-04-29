package com.liu.getOffBusReminderFinal.common;

import java.util.Arrays;

public enum ExceptionCode implements IExceptionCode {
    NOT_MODIFIED(304, "未修改"),
    BAD_REQUEST(400, "请求参数有误,比如请求头不对、参数验证失败等"),
    UNAUTHORIZED(401, "未登录或登录已过期"),
    FORBIDDEN(403, "没有权限"),
    NOT_FOUND(404, "未找到记录"),
    INTERNAL_SERVER_ERROR(500, "业务处理错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用");

    private final int value;
    private final String defaultMessage;

    private ExceptionCode(int value, String defaultMessage) {
        this.value = value;
        this.defaultMessage = defaultMessage;
    }

    public int value() {
        return this.value;
    }

    public String defaultMessage() {
        return this.defaultMessage;
    }

    public static ExceptionCode getValue(Integer value) {
        return null == value ? null : (ExceptionCode) Arrays.stream(values()).filter((v) -> {
            return value == v.value;
        }).findFirst().orElse(null);
    }
}
