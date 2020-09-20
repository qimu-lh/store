package com.ytu.jierui.store.mapper;

import com.ytu.jierui.store.entity.Admin;
import com.ytu.jierui.store.entity.Shop;

import java.util.List;

public interface ShopMapper {

    List<Shop> selectAllShop();

    Shop selectByConditions(String shopname, String storename);

    Integer updateStatusBySid(Integer sid,Integer status);
}
