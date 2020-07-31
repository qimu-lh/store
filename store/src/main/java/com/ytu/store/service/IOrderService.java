package com.ytu.store.service;

import com.ytu.store.entity.OrderUser;
import com.ytu.store.service.ex.InsertException;

import java.util.List;

public interface IOrderService {

    OrderUser create(Integer aid, Integer uid, String username) throws InsertException;

    List<OrderUser> getOrders();

    List<OrderUser> getOrdersByUid(Integer uid);

    void operatorChangeState(Integer oid,String state);

    void supplierChangeState(Integer oid,String state);

    Integer getSalesByState();

    OrderUser getOrderUserByOid(Integer oid);
}
