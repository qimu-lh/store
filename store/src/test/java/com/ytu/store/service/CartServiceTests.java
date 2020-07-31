package com.ytu.store.service;

import com.ytu.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {

    @Autowired
    private ICartService service;

    @Test
    public void addToCart() {
        try {
            Integer uid = 3;
            Integer pid = 1;
            Integer num = 1;
            String username = "HAHAHA";
            service.addToCart(uid, pid, num, username);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void deleteByUid(){
        Integer uid=1;
        service.deleteByUid(uid);
        System.err.println("ok.");
    }
}