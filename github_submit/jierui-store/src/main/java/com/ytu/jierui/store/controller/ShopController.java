package com.ytu.jierui.store.controller;

import com.ytu.jierui.store.entity.Admin;
import com.ytu.jierui.store.entity.Shop;
import com.ytu.jierui.store.service.IShopService;
import com.ytu.jierui.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("shops")
public class ShopController extends BaseController{

    @Autowired
    private IShopService shopService;

    @GetMapping("/")
    public JsonResult<List<Shop>> getAllShop(){
        List<Shop> data = shopService.findAllShop();
        return new JsonResult<>(SUCCESS,data);
    }

    @GetMapping("{shopname}/findShopByCondition/{storename}")
    public JsonResult<Shop> getShopByCondition(@PathVariable("shopname") String shopname,
                                                 @PathVariable("storename") String storename){
        Shop data=shopService.findByConditions(shopname,storename);
        return new JsonResult<>(SUCCESS,data);
    }

    @RequestMapping("{sid}/changeStatus/{status}")
    public JsonResult<Void> changeStatus(@PathVariable("sid") Integer sid,
                                         @PathVariable("status") Integer status){
        shopService.changeStatusBySid(sid,status);
        // 返回成功，及操作结果
        return new JsonResult<>(SUCCESS);
    }
}
