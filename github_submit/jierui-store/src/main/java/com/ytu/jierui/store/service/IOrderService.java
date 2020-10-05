package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.OrderUser;

import java.util.List;

public interface IOrderService {

    void createOrderUser(String username,String totalPrice);

    List<OrderUser> getAll();

    void addLogisticsByOid(String logistics,Integer oid);
}
