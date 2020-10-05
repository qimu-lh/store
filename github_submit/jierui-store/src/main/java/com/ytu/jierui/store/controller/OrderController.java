package com.ytu.jierui.store.controller;

import com.ytu.jierui.store.entity.Admin;
import com.ytu.jierui.store.entity.OrderUser;
import com.ytu.jierui.store.service.IOrderService;
import com.ytu.jierui.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController{

    @Autowired
    private IOrderService orderService;

    @RequestMapping("{totalPrice}/create")
    public JsonResult<Void> create(@PathVariable("totalPrice") String totalPrice,
                                   HttpSession session) {
        String username=getUsernameFromSession(session);
        orderService.createOrderUser(username,totalPrice);
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("/")
    public JsonResult<List<OrderUser>> getAllOrders(){
        List<OrderUser> data = orderService.getAll();
        return new JsonResult<>(SUCCESS,data);
    }

    @RequestMapping("{logistics}/addLogistics/{oid}")
    public JsonResult<Void> addLogistics(@PathVariable("logistics") String logistics,
                                         @PathVariable("oid") Integer oid){
        orderService.addLogisticsByOid(logistics,oid);
        return new JsonResult<>(SUCCESS);
    }
}
