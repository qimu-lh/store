package com.ytu.jierui.store.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartVO implements Serializable {

    private Integer cid;
    private Integer pid;
    private Integer uid;
    private String image;
    private String name;
    private String shop;
    private Long price;
    private Long realPrice;
    private Integer num;
}
