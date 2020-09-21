package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.Admin;
import com.ytu.jierui.store.entity.Shop;

import java.util.List;

public interface IShopService {

    List<Shop> findAllShop();

    void changeStatusBySid(Integer sid,Integer status);

    Shop findByConditions(String shopname, String storename);
}
