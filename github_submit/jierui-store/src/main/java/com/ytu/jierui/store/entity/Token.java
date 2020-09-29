package com.ytu.jierui.store.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Token {

    private Integer uid;
    private String autoLoginToken;
    private String tokenType;
    private Date overdueTime;
}
