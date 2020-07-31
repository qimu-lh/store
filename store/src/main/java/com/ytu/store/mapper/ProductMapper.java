package com.ytu.store.mapper;

import com.ytu.store.entity.Product;

import java.util.List;

public interface ProductMapper {

    Integer saveProduct(Product product);

    Integer deleteByPid(Integer pid);

    Integer updateNum(Integer num,Integer pid);

    Product findByPid(Integer pid);

    Product findByName(String name);

    List<Product> findHotList();

    List<Product> findProductsList();

    List<Product> findByStr(String str);
}
