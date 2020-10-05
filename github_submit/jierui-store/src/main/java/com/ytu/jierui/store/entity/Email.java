package com.ytu.jierui.store.entity;

import lombok.Data;

@Data
public class Email {

    private String sender;
    private String receiver;
    private String subject;
    private String context;
}
