package com.ytu.jierui.store.entity;

import lombok.Data;

@Data
public class Boss extends BaseEntity{

    private Integer bid;
    private String bossname;
    private String password;
    private String salt;
}
