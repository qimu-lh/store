package com.ytu.store.service.ex;

public class AddressSizeLimitException extends ServiceException{
    public AddressSizeLimitException() {
    }

    public AddressSizeLimitException(String message) {
        super(message);
    }

    public AddressSizeLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressSizeLimitException(Throwable cause) {
        super(cause);
    }

    public AddressSizeLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
