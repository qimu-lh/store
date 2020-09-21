package com.ytu.jierui.store.entity;

import lombok.Data;

@Data
public class Product {

    private Integer pid;
    private String name;
    private Integer num;
    private Long price;
    private String shop;
    private String information;
    private String image;
}
