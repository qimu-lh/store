package com.ytu.store.service;

import com.ytu.store.entity.User;
import com.ytu.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    IUserService iUserService;

    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("service");
            user.setPassword("1234");
            iUserService.reg(user);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        String username="naruto";
        String password="1111";
        User user = iUserService.login(username, password);
        System.err.println(user);
    }

    @Test
    public void changePassword() {
        try {
            Integer uid = 3;
            String username = "密码管理员";
            String oldPassword = "1111";
            String newPassword = "1111";
            iUserService.changePassword(uid, username, oldPassword, newPassword);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void getByUid() {
        try {
            Integer uid = 3;
            User result = iUserService.getByUid(uid);
            System.err.println(result);
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void changeInfo() {
        try {
            Integer uid = 3;
            String username = "资料管理员";
            User user = new User();
            user.setGender(1);
            user.setPhone("13100131010");
            user.setEmail("root@163.com");
            iUserService.changeInfo(uid, username, user);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void resetPassword(){
        Integer code=1234;
        String email="124@qq.com";
        String password="2222";
        iUserService.resetPassword(code,email,password);
        System.err.println("ok.");
    }
}
