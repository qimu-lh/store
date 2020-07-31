package com.ytu.store.service;
import com.ytu.store.entity.OrderUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTests {

    @Autowired
    private IOrderService service;

    @Test
    public void create() {
        Integer aid = 2;
        Integer uid = 7;
        String username = "超级管理员";
        OrderUser order = service.create(aid, uid, username);
        System.err.println(order);
    }

    @Test
    public void findOrdersByUid(){
        List<OrderUser> data = service.getOrdersByUid(3);
        for (OrderUser orderUser:
                data) {
            System.err.println(orderUser);
        }
    }

    @Test
    public void supplierChangeState(){
        Integer oid=1;
        String state="Delivered";
        service.supplierChangeState(oid,state);
        System.err.println("ok.");
    }
}
