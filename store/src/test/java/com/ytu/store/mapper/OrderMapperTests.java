package com.ytu.store.mapper;

import com.ytu.store.entity.OrderItem;
import com.ytu.store.entity.OrderUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTests {

    @Autowired
    private OrderMapper mapper;

    @Test
    public void insertOrder() {
        OrderUser order = new OrderUser();
        order.setUid(1);
        Integer rows = mapper.insertOrderUser(order);
        System.err.println("rows=" + rows);
    }

    @Test
    public void insertOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(1);
        Integer rows = mapper.insertOrderItem(orderItem);
        System.err.println("rows=" + rows);
    }

    @Test
    public void updateState(){
        Integer oid=3;
        String state="Delivered";
        Integer rows = mapper.updateState(oid, state);
        System.err.println("rows="+rows);
    }

    @Test
    public void findOrders(){
        List<OrderUser> data = mapper.findOrders();
        for (OrderUser orderUser:
             data) {
            System.err.println(orderUser);
        }
    }

    @Test
    public void findOrdersByUid(){
        Integer uid=3;
        List<OrderUser> data = mapper.findOrdersByUid(uid);
        for (OrderUser orderUser:
                data) {
            System.err.println(orderUser);
        }
    }

    @Test
    public void findState(){
        Integer s= mapper.findSalesByState();
        System.err.println(s);
    }

    @Test
    public void findOrderItemByOid(){
        Integer oid=4;
        List<OrderItem> orderItem=mapper.findOrderItemByOid(oid);
        for (OrderItem oi :
                orderItem) {
            System.err.println(oi);
        }
    }

    @Test
    public void findOrderUserByOid(){
        Integer oid=1;
        System.err.println(mapper.findOrderUserByOid(oid));
    }
}
