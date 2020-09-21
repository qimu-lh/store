package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> getHotList(Integer currentPage,Integer pageNum);

    Product findByPid(Integer pid);
}
