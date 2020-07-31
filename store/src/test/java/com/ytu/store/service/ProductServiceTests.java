package com.ytu.store.service;

import com.ytu.store.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private IProductService service;

    @Test
    public void findHotList() {
        List<Product> list = service.getHotList();
        System.err.println("count=" + list.size());
        for (Product item : list) {
            System.err.println(item);
        }
    }
    @Test
    public void getByPid() {
        Integer pid = 1;
        Product result = service.findByPid(pid);
        System.err.println(result);
    }

    @Test
    public void getByStr() {
        String str="ip";
        List<Product> list = service.getByStr(str);
        System.err.println("count=" + list.size());
        for (Product item : list) {
            System.err.println(item);
        }
    }


}
