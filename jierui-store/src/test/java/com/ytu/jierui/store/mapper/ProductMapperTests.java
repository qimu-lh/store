package com.ytu.jierui.store.mapper;

import com.ytu.jierui.store.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperTests {

    @Autowired
    ProductMapper productMapper;

    @Test
    public void findPage(){
        List<Product> hotList = productMapper.findHotList(4, 4);
        for (Product list:hotList) {
            System.out.println(list);
        }
    }
}
