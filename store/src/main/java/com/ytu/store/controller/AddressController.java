package com.ytu.store.controller;

import com.ytu.store.entity.Address;
import com.ytu.store.service.IAddressService;
import com.ytu.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController {

    @Autowired
    private IAddressService addressService;

    @RequestMapping("addnew")
    public JsonResult<Void> addnew(Address address, HttpSession session) {
        // 从session中获取uid和username
        Integer uid=getUidFromSession(session);
        String username=getUsernameFromSession(session);
        // 执行增加
        addressService.addnew(address,uid,username);
        // 响应成功
        return new JsonResult<>(SUCCESS);
    }

    @GetMapping("/")
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        // 从session中获取uid
        Integer uid = getUidFromSession(session);
        // 调用业务层对象查询数据
        List<Address> data = addressService.getByUid(uid);
        // 响应成功，及查询到的数据
        return new JsonResult<>(SUCCESS, data);
    }

    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(
            @PathVariable("aid") Integer aid,
            HttpSession session) {
        // 从session中获取uid, username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 执行设置默认
        addressService.setDefault(aid, uid, username);
        // 响应操作成功
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("{aid}/delete")
    public JsonResult<Void> delete(
            @PathVariable("aid") Integer aid,
            HttpSession session) {
        // 从Session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务层对象执行设置默认
        addressService.delete(aid, uid, username);
        // 响应成功
        return new JsonResult<>(SUCCESS);
    }

    @GetMapping("get_isDefault_Address")
    public JsonResult<Address> getIsDefaultAddress(HttpSession session){
        Integer uid =getUidFromSession(session);
        Address data=addressService.getAddress(uid);
        return new JsonResult<Address>(SUCCESS,data);
    }
}
