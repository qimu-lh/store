package com.ytu.store.service;

import com.ytu.store.entity.Product;

import java.util.List;

public interface IProductService {

    void addProduct(Product product);

    void deleteProductByPid(Integer pid);

    Product findByPid(Integer pid);

    List<Product> getHotList();

    List<Product> getProductsList();

    List<Product> getByStr(String str);
}
