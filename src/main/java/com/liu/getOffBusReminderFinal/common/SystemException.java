package com.liu.getOffBusReminderFinal.common;

public class SystemException extends BusinessException {
    private static final long serialVersionUID = 8996110607302347653L;

    public SystemException() {
        super(ExceptionCode.INTERNAL_SERVER_ERROR, ExceptionCode.INTERNAL_SERVER_ERROR.defaultMessage());
    }

    public SystemException(String message) {
        super(ExceptionCode.INTERNAL_SERVER_ERROR, message);
    }

    public SystemException(ExceptionCode code) {
        super(code, code.defaultMessage());
    }

    public SystemException(ExceptionCode code, String message) {
        super(code, message);
    }

    public SystemException(Throwable throwable) {
        super(ExceptionCode.INTERNAL_SERVER_ERROR, throwable);
    }
}
