package com.ytu.store.service;

import com.ytu.store.entity.User;

public interface IUserService {

    void reg(User user);

    User login(String username, String password);

    void changePassword(Integer uid, String username, String oldPassword, String newPassword);


    User getByUid(Integer uid);

    void changeInfo(Integer uid, String username, User user);

    void resetPassword(Integer code,String email,String password);
}
