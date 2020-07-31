package com.ytu.store.controller;

import com.ytu.store.entity.OrderUser;
import com.ytu.store.service.IEmailService;
import com.ytu.store.service.IOrderService;
import com.ytu.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IEmailService emailService;

    @RequestMapping("{aid}/create")
    public JsonResult<OrderUser> create(
            @PathVariable("aid") Integer aid,
            HttpSession session) {
        // 从Session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务层对象执行
        OrderUser data = orderService.create(aid, uid, username);
        // 响应
        return new JsonResult<>(SUCCESS, data);
    }

    @GetMapping("user")
    public JsonResult<List<OrderUser>> getUserOrder(HttpSession session){
        Integer uid=getUidFromSession(session);
        List<OrderUser> data = orderService.getOrdersByUid(uid);
        return new JsonResult<>(SUCCESS,data);
    }

    @GetMapping("/")
    public JsonResult<List<OrderUser>> getOrder(){
        List<OrderUser> data = orderService.getOrders();
        return new JsonResult<>(SUCCESS,data);
    }

    @RequestMapping("{oid}/{state}/operator_set_state")
    public JsonResult<Void> operatorState(@PathVariable("oid") Integer oid,
                                         @PathVariable("state") String state){
        orderService.operatorChangeState(oid, state);
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("{oid}/{state}/supplier_set_state")
    public JsonResult<Void> supplierState(@PathVariable("oid") Integer oid,
                                        @PathVariable("state") String state){
        orderService.supplierChangeState(oid, state);
        return new JsonResult<>(SUCCESS);
    }

    @GetMapping("operator_get_sales")
    public JsonResult<Integer> getSales(){
        Integer data = orderService.getSalesByState();
        return new JsonResult<>(SUCCESS,data);
    }

    @GetMapping("{oid}/send_order_information")
    public JsonResult<Void> sendOrderInformation(@PathVariable("oid") Integer oid){
        emailService.sendOrderInformation(oid);
        return new JsonResult<>(SUCCESS);
    }
}