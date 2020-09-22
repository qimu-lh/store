package com.ytu.jierui.store.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Boss extends BaseEntity{

    private Integer bid;
    private String bossname;
    private String password;
    private String salt;
    private Integer lockNum;
    private Date lockTime;
    private Date unlockTime;
}
