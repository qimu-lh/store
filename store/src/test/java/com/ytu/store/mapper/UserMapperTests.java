package com.ytu.store.mapper;

import com.ytu.store.entity.Operator;
import com.ytu.store.entity.Supplier;
import com.ytu.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OperatorMapper operatorMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Test
    public void findByOpertorname() {
        String name = "operator";
        Operator result = operatorMapper.findByUsername(name);
        System.err.println(result);
    }

    @Test
    public void findBySuppliername() {
        String name = "supplier";
        Supplier result = supplierMapper.findByUsername(name);
        System.err.println(result);
    }

    @Test
    public void save() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("1234");
        Integer rows = userMapper.save(user);
        System.err.println("rows=" + rows);
    }

    @Test
    public void updatePassword(){
        Integer uid=1;
        String password="1111";
        String modifiedUser="超级管理员";
        Date now=new Date();
        Integer rows = userMapper.updatePassword(uid, password, modifiedUser, now);
        System.err.println("rows="+rows);
    }

    @Test
    public void updateCode(){
        Integer code=2222;
        String email="123@qq.com";
        Integer rows = userMapper.updateCode(code, email);
        System.err.println("rows="+rows);
    }

    @Test
    public void findByUsername() {
        String username = "admin";
        User result = userMapper.findByUsername(username);
        System.err.println(result);
    }

    @Test
    public void findByUid(){
        Integer uid=1;
        User user = userMapper.findByUid(uid);
        System.err.println(user);
    }

    @Test
    public void updateInfo() {
        User user = new User();
        user.setUid(3);
        user.setGender(0);
        user.setPhone("13800138001");
        user.setEmail("root@qq.com");
        Integer rows = userMapper.updateInfo(user);
        System.err.println("rows=" + rows);
    }

    @Test
    public void findByEmail(){
        String email="123@qq.com";
        System.err.println(userMapper.findByEmail(email));
    }

    @Test
    public void findEmails(){
        List<User> users = userMapper.findUsers();
        for (User user :
                users) {
            System.err.println(user.getEmail());
        }
    }

    @Test
    public void resetPasswordByEmail(){
        Integer code=1111;
        String email="1111@11.com";
        String password="2222";
        Integer rows = userMapper.resetPasswordByEmail(code, email, password);
        System.err.println(rows);
    }
}
