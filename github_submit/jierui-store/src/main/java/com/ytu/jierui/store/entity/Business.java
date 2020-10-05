package com.ytu.jierui.store.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Business {

    private Integer businessId;
    private String businessName;
    private String idCard;
    private String province;
    private String city;
    private String district;
    private String address;
    private String phone;
    private String email;
    private Integer status;
    private Date applyTime;
}
