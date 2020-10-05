package com.ytu.jierui.store.service.impl;

import com.ytu.jierui.store.entity.Email;
import com.ytu.jierui.store.service.IEmailService;
import com.ytu.jierui.store.service.ex.EmailSendWrongException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleEmail(Email email) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(email.getSender());
        message.setTo(email.getReceiver());
        message.setSubject(email.getSubject());
        message.setText(email.getContext());
        try {
            mailSender.send(message);
        }catch (Exception e){
            throw new EmailSendWrongException("发送邮件时出现未知异常！请检查个人邮箱是否正确！");
        }
    }
}
