package com.ytu.jierui.store.mapper;

import com.ytu.jierui.store.entity.Product;

import java.util.List;

public interface ProductMapper {

    List<Product> findHotList(Integer currentPage,Integer pageNum);

    Product findByPid(Integer pid);

    List<Product> findByStr(String str);
}
