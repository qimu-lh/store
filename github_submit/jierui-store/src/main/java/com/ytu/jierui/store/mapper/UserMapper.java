package com.ytu.jierui.store.mapper;

import com.ytu.jierui.store.entity.User;

import java.util.Date;

public interface UserMapper {

    //注册
    Integer save(User user);

    //根据用户名 或手机号 查询用户信息
    User selectByUsername(String username);

}

