package com.ytu.store.service.impl;

import com.ytu.store.entity.Product;
import com.ytu.store.mapper.ProductMapper;
import com.ytu.store.service.IProductService;
import com.ytu.store.service.ex.DeleteException;
import com.ytu.store.service.ex.InsertException;
import com.ytu.store.service.ex.ProductConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addProduct(Product product) {
        String name=product.getName();
        Product result=productMapper.findByName(name);
        // 判断查询结果是否不为null
        if (result != null) {
            // 是：商品已存在，不允许注册，抛出ProductConflictException异常
            throw new ProductConflictException("商品已存在！");
        }
        // 补全数据：4项日志，用户名为注册的用户名，时间为当前时间
        Date now = new Date();
        product.setCreatedUser("供应商");
        product.setCreatedTime(now);
        product.setModifiedUser("供应商");
        product.setModifiedTime(now);
        //补全图片路径
        product.setImage("/assets/images/shop/products/"+product.getImage());
        Integer rows = productMapper.saveProduct(product);
        // 判断以上返回值是否不为1
        if (rows != 1) {
            // 是：插入用户数据失败，抛出InsertException
            throw new InsertException("插入数据时出现未知错误");
        }
    }

    @Override
    public void deleteProductByPid(Integer pid) {
        Integer rows = productMapper.deleteByPid(pid);
        if (rows!=1){
            throw new DeleteException("删除数据时出现异常！");
        }
    }

    @Override
    public List<Product> getHotList() {
        List<Product> list = productMapper.findHotList();
        for (Product product : list) {
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public List<Product> getProductsList() {
        List<Product> list = productMapper.findProductsList();
        for (Product product : list) {
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public Product findByPid(Integer pid) {
        Product product = productMapper.findByPid(pid);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);
        return product;
    }

    @Override
    public List<Product> getByStr(String str) {
        List<Product> list = productMapper.findByStr(str);
        for (Product product : list) {
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return list;
    }

}
