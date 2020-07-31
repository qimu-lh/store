package com.ytu.store.service;

public interface IEmailService {

    //发送者邮件地址
    String SENDER_EMAIL ="1908773440@qq.com";

    void sendCodeEmail(String email);

    void sendOrderInformation(Integer oid);
}
