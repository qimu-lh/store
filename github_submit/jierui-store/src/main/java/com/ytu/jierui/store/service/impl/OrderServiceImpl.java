package com.ytu.jierui.store.service.impl;

import com.ytu.jierui.store.entity.OrderUser;
import com.ytu.jierui.store.entity.User;
import com.ytu.jierui.store.mapper.OrderMapper;
import com.ytu.jierui.store.mapper.UserMapper;
import com.ytu.jierui.store.service.IOrderService;
import com.ytu.jierui.store.service.ex.InsertException;
import com.ytu.jierui.store.service.ex.UpdateException;
import com.ytu.jierui.store.service.ex.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void createOrderUser(String username, String totalPrice) {
        User user=userMapper.selectByUsername(username);
        if (user==null){
            throw new UserNotFoundException("用户信息不存在，请检查登录状态");
        }
        OrderUser orderUser=new OrderUser();
        orderUser.setUid(user.getUid());
        orderUser.setRecvName(user.getUsername());
        orderUser.setRecvPhone(user.getPhone());
        orderUser.setLogistics("无");
        orderUser.setState(0);
        orderUser.setOrderTime(new Date());
        orderUser.setTotalPrice(Long.valueOf(totalPrice));

        Integer rows = orderMapper.insertOrderUser(orderUser);
        if (rows!=1){
            throw new InsertException("插入数据时出现未知错误");
        }
    }

    @Override
    public List<OrderUser> getAll() {
        return orderMapper.selectAll();
    }

    @Override
    public void addLogisticsByOid(String logistics, Integer oid) {
        Integer rows = orderMapper.updateLogisticsByOid(logistics, oid);
        if (rows!=1){
            throw new UpdateException("更新数据时出现错误");
        }
    }
}
