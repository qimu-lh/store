package com.ytu.jierui.store.service.impl;

import com.ytu.jierui.store.entity.Product;
import com.ytu.jierui.store.mapper.ProductMapper;
import com.ytu.jierui.store.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getHotList(Integer currentPage,Integer pageNum) {
        List<Product> list = productMapper.findHotList(currentPage,pageNum);
        return list;
    }

    @Override
    public Product findByPid(Integer pid) {
        return productMapper.findByPid(pid);
    }
}
