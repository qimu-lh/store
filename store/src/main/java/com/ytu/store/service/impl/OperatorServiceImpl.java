package com.ytu.store.service.impl;

import com.ytu.store.entity.Operator;
import com.ytu.store.mapper.OperatorMapper;
import com.ytu.store.service.IOperatorService;
import com.ytu.store.service.ex.PasswordNotMatchException;
import com.ytu.store.service.ex.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorServiceImpl implements IOperatorService {

    @Autowired
    OperatorMapper operatorMapper;

    @Override
    public Operator login(String operatorName, String password) {
        // 根据参数username查询用户数据
        Operator result = operatorMapper.findByUsername(operatorName);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：用户名不存在，抛出UserNotFoundException
            throw new UserNotFoundException("用户名不存在");
        }
        // 判断输入的密码与查询结果中的密码是否不匹配
        if (!result.getPassword().equals(password)) {
            // 是：密码错误，抛出PasswordNotMatchException
            throw new PasswordNotMatchException("密码错误");
        }
        // 将查询结果中不应该返回的字段设置为null
        result.setCreatedUser(null);
        result.setCreatedTime(null);
        result.setModifiedUser(null);
        result.setModifiedTime(null);
        result.setPassword(null);
        // 返回查询结果
        return result;
    }
}
