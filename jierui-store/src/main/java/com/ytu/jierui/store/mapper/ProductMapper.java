package com.ytu.jierui.store.mapper;

import com.ytu.jierui.store.entity.Product;

import java.util.List;

public interface ProductMapper {

    List<Product> findHotList();

    Product findByPid(Integer pid);
}
