package com.ytu.jierui.store.mapper;


import com.ytu.jierui.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {

    @Autowired
    UserMapper userMapper;

    @Test
    public void save(){
        User user=new User();
        user.setUsername("test02");
        user.setPassword("1234");
        user.setPhone("123");
        userMapper.save(user);
        System.out.println("OK.");
    }

    @Test
    public void findByUP(){
        User user = userMapper.selectByUsername("123");
        System.out.println(user);
    }
}
