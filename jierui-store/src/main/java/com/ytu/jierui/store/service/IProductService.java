package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> getHotList();

    Product findByPid(Integer pid);
}
