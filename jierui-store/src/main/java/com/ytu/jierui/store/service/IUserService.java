package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.User;

import java.util.Date;

public interface IUserService {

    void reg(User user);

    User login(String username,String password);

}
