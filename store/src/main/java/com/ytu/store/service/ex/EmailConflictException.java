package com.ytu.store.service.ex;

public class EmailConflictException extends ServiceException{
    public EmailConflictException() {
    }

    public EmailConflictException(String message) {
        super(message);
    }

    public EmailConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailConflictException(Throwable cause) {
        super(cause);
    }

    public EmailConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
