package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private IUserService iUserService;

    @Test
    public void reg(){
        User user=new User();
        user.setUsername("test03");
        user.setPassword("1234");
        user.setPhone("12345");
        iUserService.reg(user);
        System.out.println("注册成功 --> OK.");
    }

    @Test
    public void login(){
        User test14 = iUserService.login("test11", "1");
        System.out.println(test14);
    }
}
