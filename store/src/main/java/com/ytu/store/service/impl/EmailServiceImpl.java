package com.ytu.store.service.impl;

import com.ytu.store.entity.OrderUser;
import com.ytu.store.entity.User;
import com.ytu.store.mapper.UserMapper;
import com.ytu.store.service.IEmailService;
import com.ytu.store.service.IOrderService;
import com.ytu.store.service.ex.EmailSendWrongException;
import com.ytu.store.service.ex.UpdateException;
import com.ytu.store.service.ex.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IOrderService orderService;

    @Override
    public void sendCodeEmail(String email) {
        String verificationCode = String.valueOf((int)((Math.random()*9+1)*1000));
        User result=userMapper.findByEmail(email);
        if (result==null){
            throw new UserNotFoundException("该用户不存在！");
        }
        Integer rows = userMapper.updateCode(Integer.parseInt(verificationCode), email);
        if (rows!=1){
            throw new UpdateException("更新数据时出现异常！");
        }
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(SENDER_EMAIL);
        message.setTo(email);
        message.setSubject("验证码（YTUStore）");
        message.setText(verificationCode);
        try {
            mailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
            throw new EmailSendWrongException("发送邮件时出现未知异常！请检查个人邮箱是否正确！");
        }
    }

    @Override
    public void sendOrderInformation(Integer oid) {
        OrderUser orderUser = orderService.getOrderUserByOid(oid);
        String receiver=orderUser.getRecvEmail();
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(SENDER_EMAIL);
        message.setTo(receiver);
        message.setSubject("订单消息（YTUStore）");
        //时间按照自定义格式输出
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderCreateTime=formatter.format(orderUser.getOrderTime());
        String content="订单号："+orderUser.getOid()+"，购买日期："+orderCreateTime
                +"，总额（￥）："+orderUser.getTotalPrice()+"，订单状态："+orderUser.getState();
        message.setText(content);
        try {
            mailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
            throw new EmailSendWrongException("发送邮件时出现未知异常！请检查个人邮箱是否正确！");
        }
    }
}
