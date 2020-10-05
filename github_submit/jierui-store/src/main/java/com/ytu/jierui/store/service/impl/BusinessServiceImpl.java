package com.ytu.jierui.store.service.impl;

import com.ytu.jierui.store.entity.Business;
import com.ytu.jierui.store.mapper.BusinessMapper;
import com.ytu.jierui.store.service.IBusinessService;
import com.ytu.jierui.store.service.ex.InsertException;
import com.ytu.jierui.store.service.ex.UsernameConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BusinessServiceImpl implements IBusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    @Override
    public void reg(Business business) {
        String idCard = business.getIdCard();
        String email=business.getEmail();
        //根据idCard查询用户，以确保身份证唯一
        Business result01 = businessMapper.selectByCardId(idCard);
        //根据邮箱查询用户，以确保邮箱唯一
        Business result02 = businessMapper.selectByEmail(email);
        // 判断查询结果是否不为null
        if (result01 != null) {
            // 是：用户名已存在，不允许注册，抛出UsernameConflictException异常
            throw new UsernameConflictException("身份证号为(" + idCard + ")已经被占用！");
        }
        if (result02 != null) {
            // 是：用户名已存在，不允许注册，抛出UsernameConflictException异常
            throw new UsernameConflictException("邮箱为(" + email + ")已经被占用！");
        }
        // 参数business是客户端提交的注册数据，并不包含隐藏数据
        // 补全数据：status，值为0
        business.setStatus(0);
        // 补全数据：ApplyTime，时间为当前时间
        Date now = new Date();
        business.setApplyTime(now);

        // 调用userMapper的功能插入用户数据，实现注册，并获取插入数据时的返回值
        Integer rows = businessMapper.save(business);
        // 判断以上返回值是否不为1
        if (rows != 1) {
            // 是：插入用户数据失败，抛出InsertException
            throw new InsertException("插入用户数据时出现未知错误");
        }
    }

    @Override
    public void updateId(String idCard, String email) {
        Integer rows = businessMapper.updateIdByEmail(idCard, email);
        // 判断以上返回值是否不为1
        if (rows != 1) {
            // 是：插入用户数据失败，抛出InsertException
            throw new InsertException("插入数据时出现未知错误");
        }
    }
}
