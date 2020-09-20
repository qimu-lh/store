package com.ytu.jierui.store.entity;

import lombok.Data;

@Data
public class Admin extends BaseEntity{

    private Integer aid;
    private String adminname;
    private String password;
    private String salt;
    private String belonger;
    private Integer status;
}
