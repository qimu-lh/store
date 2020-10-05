package com.ytu.jierui.store.mapper;

import com.ytu.jierui.store.entity.OrderUser;

import java.util.List;

public interface OrderMapper {

    Integer insertOrderUser(OrderUser orderUser);

    Integer updateLogisticsByOid(String logistics,Integer oid);

    List<OrderUser> selectAll();
}
