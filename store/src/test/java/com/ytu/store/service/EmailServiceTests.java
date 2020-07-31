package com.ytu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTests {

    @Autowired
    IEmailService emailService;

    @Test
    public void sendCodeEmail() throws Exception{
        String email="2191143369@qq.com";
        emailService.sendCodeEmail(email);
    }
}
