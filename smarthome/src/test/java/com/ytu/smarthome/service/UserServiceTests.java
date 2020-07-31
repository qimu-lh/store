package com.ytu.smarthome.service;

import com.ytu.smarthome.entity.User;
import com.ytu.smarthome.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    IUserService iUserService;

    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("test3");
            user.setPassword("1111");
            iUserService.reg(user);
            System.err.println("OK");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void login() {
        try {
            String username = "exception";
            String password = "1234";
            User result = iUserService.login(username, password);
            System.err.println(result);
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
    public void getByUsername() {
        try {
            String username = "root";
            User result = iUserService.getByUsername(username);
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
            String username = "test3";
            User user = new User();
            user.setTemperature(30);
            user.setHumidity(50);
            user.setAirMode("制冷");
            user.setAirTemperature(20);
            user.setAirWindSpeed("中");
            user.setLamp1("关灯");
            user.setLamp2("关灯");
            user.setLamp3("0");
            user.setLamp4("50");
            user.setWindowMode("关窗");
            iUserService.changeInfo(uid, username, user);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    //admin测试
    @Test
    public void getUsers() {
        List<User> list = iUserService.getUsers();
        System.err.println("count=" + list.size());
        for (User item : list) {
            System.err.println(item);
        }
    }
}
