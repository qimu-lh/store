package com.ytu.jierui.store.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Shop {

    private Integer sid;
    private String shopname;
    private String storename;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date applyTime;
    private Integer status;
}
