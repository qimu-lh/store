package com.ytu.jierui.store.service.ex;

public class ProductConflictException extends ServiceException{

    public ProductConflictException() {
        super();
    }

    public ProductConflictException(String message) {
        super(message);
    }

    public ProductConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductConflictException(Throwable cause) {
        super(cause);
    }

    protected ProductConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
