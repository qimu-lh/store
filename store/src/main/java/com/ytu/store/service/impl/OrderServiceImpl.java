package com.ytu.store.service.impl;

import com.ytu.store.entity.*;
import com.ytu.store.mapper.OrderMapper;
import com.ytu.store.mapper.ProductMapper;
import com.ytu.store.service.IAddressService;
import com.ytu.store.service.ICartService;
import com.ytu.store.service.IOrderService;
import com.ytu.store.service.IUserService;
import com.ytu.store.service.ex.InsertException;
import com.ytu.store.service.ex.UpdateException;
import com.ytu.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public OrderUser create(Integer aid, Integer uid, String username) throws InsertException {
        // 创建当前时间对象：now
        Date now = new Date();
        // 根据参数uid查询对应的购物车数据，得到List<CartVO>对象
        List<CartVO> carts = cartService.getByUid(uid);
        // 遍历以上查询到的对象，根据各元素的price和num计算得到总价
        Long totalPrice = 0L;
        for (CartVO cartVO : carts) {
            totalPrice += cartVO.getPrice() * cartVO.getNum();
        }

        // 根据收货地址aid查询收货地址详情
        Address address = addressService.getByAid(aid);

        // 创建orderUser对象：
        OrderUser orderUser = new OrderUser();
        // 封装order对象中的属性值：order.setUid(uid);
        orderUser.setUid(uid);
        // 封装order对象中的属性值：recv_name,recv_phone,recv_address,recv_email
        orderUser.setRecvName(address.getName());
        orderUser.setRecvPhone(address.getPhone());
        orderUser.setRecvAddress(address.getProvinceName() + address.getCityName() + address.getAreaName() + address.getAddress());
        //根据uid获得user地址,通过get方法获得email地址
        User user = userService.getByUid(uid);
        orderUser.setRecvEmail(user.getEmail());
        // 封装order对象中的属性值：total_price
        orderUser.setTotalPrice(totalPrice);
        // 封装order对象中的属性值：pay(0)
        orderUser.setPay(0);
        // 封装order对象中的属性值：state为In progress
        orderUser.setState("Delayed");
        // 封装order对象中的属性值：order_time(now)
        orderUser.setOrderTime(now);
        // 封装order对象中的属性值：pay_time(null)
        orderUser.setPayTime(null);
        // 封装order对象中的属性值：日志
        orderUser.setCreatedUser(username);
        orderUser.setCreatedTime(now);
        orderUser.setModifiedUser(username);
        orderUser.setModifiedTime(now);
        // 插入订单数据：insertOrder(order)
        insertOrderUser(orderUser);

        // 遍历以上查询得到的List<CartVO>对象
        for (CartVO cartVO : carts) {
            // 创建orderItem对象：OrderItem ordeItemr = new OrderItem();
            OrderItem orderItem = new OrderItem();
            // 封装orderItem对象中的属性值：oid
            orderItem.setOid(orderUser.getOid());
            // 封装orderItem对象中的属性值：gid,price,title,image,num
            orderItem.setPid(cartVO.getPid());
            orderItem.setPrice(cartVO.getPrice());
            orderItem.setName(cartVO.getName());
            orderItem.setImage(cartVO.getImage());
            orderItem.setNum(cartVO.getNum());
            // 封装orderItem对象中的属性值：日志
            orderItem.setCreatedUser(username);
            orderItem.setCreatedTime(now);
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(now);
            // 插入订单商品数据：insertOrderItem(itemItem)
            insertOrderItem(orderItem);
        }

        //删除购物车中对应的数据
        cartService.deleteByUid(uid);
        // 返回
        return orderUser;
    }

    @Override
    public List<OrderUser> getOrders() {
        return orderMapper.findOrders();
    }

    @Override
    public List<OrderUser> getOrdersByUid(Integer uid) {
        return orderMapper.findOrdersByUid(uid);
    }

    @Override
    public void operatorChangeState(Integer oid, String state) {
        Integer rows = orderMapper.updateState(oid, state);
        // 判断返回的受影响的行数是否不为1
        if (rows != 1) {
            // 是：更新失败，抛出UpdateException
            throw new UpdateException("更新数据时出现未知错误");
        }
    }

    @Override
    public void supplierChangeState(Integer oid, String state) {
        Integer rows = orderMapper.updateState(oid, state);
        // 判断返回的受影响的行数是否不为1
        if (rows != 1) {
            // 是：更新失败，抛出UpdateException
            throw new UpdateException("更新数据时出现未知错误");
        }
        //更新商品库存量
        if ("Delivered".equals(state)){
            List<OrderItem> orderItems = orderMapper.findOrderItemByOid(oid);
            for (OrderItem orderItem : orderItems) {
                Integer pid = orderItem.getPid();
                //通过pid查询到商品
                Product product = productMapper.findByPid(pid);
                //获得商品中的库存量并与订单中商品量相减
                Integer newNum=product.getNum()-orderItem.getNum();
                //执行更新库存量
                productMapper.updateNum(newNum, pid);
            }
        }
    }

    @Override
    public Integer getSalesByState() {
        return orderMapper.findSalesByState();
    }

    @Override
    public OrderUser getOrderUserByOid(Integer oid) {
        return orderMapper.findOrderUserByOid(oid);
    }

    /**
     * 插入订单数据
     * @param orderUser 订单数据
     * @throws InsertException 插入数据异常
     */
    private void insertOrderUser(OrderUser orderUser) throws InsertException {
        Integer rows = orderMapper.insertOrderUser(orderUser);
        if (rows != 1) {
            throw new InsertException("创建订单失败！插入订单数据时出现未知错误！");
        }
    }

    /**
     * 插入订单商品数据
     * @param orderItem 订单商品数据
     * @throws InsertException 插入数据异常
     */
    private void insertOrderItem(OrderItem orderItem) throws InsertException {
        Integer rows = orderMapper.insertOrderItem(orderItem);
        if (rows != 1) {
            throw new InsertException("创建订单失败！插入订单商品数据时出现未知错误！");
        }
    }
}
