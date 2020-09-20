package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.Product;
import com.ytu.jierui.store.mapper.ProductMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTests {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void findByPid(){
        Product pro = productMapper.findByPid(1);
        System.out.println(pro);
    }
}
