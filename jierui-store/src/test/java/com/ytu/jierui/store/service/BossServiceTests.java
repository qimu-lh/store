package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.Boss;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BossServiceTests {

    @Autowired
    private IBossService iBossService;

    @Test
    public void reg(){
        Boss boss=new Boss();
        boss.setBossname("boss");
        boss.setPassword("root");
        iBossService.reg(boss);
        System.out.println("OK.");
    }

    @Test
    public void login(){
        Boss boss = iBossService.login("boss", "root");
        System.out.println(boss);
    }
}
