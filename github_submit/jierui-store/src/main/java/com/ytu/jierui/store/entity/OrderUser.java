package com.ytu.jierui.store.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OrderUser {

    private Integer oid;
    private Integer uid;
    private String  recvName;
    private String  recvPhone;
    private String  logistics;
    private Integer  state;
    private Date orderTime;
    private Long  totalPrice;
}
