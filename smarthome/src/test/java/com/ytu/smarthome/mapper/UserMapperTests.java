package com.ytu.smarthome.mapper;

import com.ytu.smarthome.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {

    @Autowired
    UserMapper userMapper;

    @Test
    public void save() {
        User user = new User();
        user.setUsername("test1");
        user.setPassword("1234");
        //返回执行成功的行数 1
        Integer rows = userMapper.save(user);
        System.err.println("rows=" + rows);
    }

    @Test
    public void updateInfo() {
        User user = new User();
        user.setUid(1);
        user.setTemperature(17);
        Integer rows = userMapper.updateInfo(user);
        System.err.println("rows=" + rows);
    }

    @Test
    public void findByUid() {
        Integer uid = 4;
        User result = userMapper.findByUid(uid);
        System.err.println(result);
    }

    @Test
    public void findByUsername() {
        String username = "join";
        User result = userMapper.findByUsername(username);
        System.err.println(result);
    }

    //admin测试
    @Test
    public void findUsers() {
        List<User> list = userMapper.findUsers();
        System.err.println("count=" + list.size());
        for (User item : list) {
            System.err.println(item);
        }
    }
}
