package com.ytu.store.mapper;

import com.ytu.store.entity.OrderItem;
import com.ytu.store.entity.OrderUser;

import java.util.List;

public interface OrderMapper {

    Integer insertOrderUser(OrderUser orderUser);

    Integer insertOrderItem(OrderItem orderItem);

    Integer updateState(Integer oid,String state);

    List<OrderUser> findOrders();

    List<OrderUser> findOrdersByUid(Integer uid);

    Integer findSalesByState();

    List<OrderItem> findOrderItemByOid(Integer oid);

    OrderUser findOrderUserByOid(Integer oid);

}
