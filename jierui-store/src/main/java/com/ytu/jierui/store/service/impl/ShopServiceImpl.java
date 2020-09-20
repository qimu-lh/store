package com.ytu.jierui.store.service.impl;

import com.ytu.jierui.store.entity.Admin;
import com.ytu.jierui.store.entity.Shop;
import com.ytu.jierui.store.mapper.ShopMapper;
import com.ytu.jierui.store.service.IShopService;
import com.ytu.jierui.store.service.ex.UpdateException;
import com.ytu.jierui.store.service.ex.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements IShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public List<Shop> findAllShop() {
        return shopMapper.selectAllShop();
    }

    @Override
    public void changeStatusBySid(Integer sid, Integer status) {
        Integer rows = shopMapper.updateStatusBySid(sid,status);
        if (rows != 1) {
            // 是：更新失败，抛出UpdateException
            throw new UpdateException("更新数据时出现未知错误");
        }
    }

    @Override
    public Shop findByConditions(String shopname, String storename) {
        Shop result=shopMapper.selectByConditions(shopname,storename);
        if (result==null){
            throw new UserNotFoundException("符合该条件的店铺不存在！");
        }
        return result;
    }
}
