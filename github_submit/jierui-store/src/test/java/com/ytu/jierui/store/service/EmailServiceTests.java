package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.Email;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private IEmailService emailService;

    @Test
    public void sendEmail(){
        Email email=new Email();
        email.setSender("1908773440@qq.com");
        email.setContext("测试");
        email.setSubject("这是一封测试邮件");
        email.setReceiver("2191143369@qq.com");
        emailService.sendSimpleEmail(email);
    }
}
