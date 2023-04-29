package com.liu.getOffBusReminderFinal.common;

public abstract class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -3489073206191573929L;
    private final IExceptionCode code;
    private final String message;

    public BusinessException(IExceptionCode code, String message) {
        this.code = code;
        if ((null == message || message.length() == 0) && null != code) {
            message = code.defaultMessage();
        }

        this.message = message;
    }

    public BusinessException(IExceptionCode code, Throwable throwable) {
        super(throwable);
        this.code = code;
        if (null == throwable && null != code) {
            this.message = code.defaultMessage();
        } else if (null != throwable) {
            this.message = throwable.getMessage();
        } else {
            this.message = null;
        }

    }

    public IExceptionCode getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
