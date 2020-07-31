package com.ytu.smarthome.service.ex;

public class InsertException extends ServiceException {
    public InsertException() {
        super();
    }

    public InsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsertException(String message) {
        super(message);
    }

    public InsertException(Throwable cause) {
        super(cause);
    }
}
