package com.ytu.store.service.ex;

public class ProductConflictException extends ServiceException{
    public ProductConflictException() {
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

    public ProductConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
