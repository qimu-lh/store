package com.ytu.jierui.store.service.ex;

public class EmailSendWrongException extends ServiceException{

    public EmailSendWrongException() {
    }

    public EmailSendWrongException(String message) {
        super(message);
    }

    public EmailSendWrongException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailSendWrongException(Throwable cause) {
        super(cause);
    }

    public EmailSendWrongException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
