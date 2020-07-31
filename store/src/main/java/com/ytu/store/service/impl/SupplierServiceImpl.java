package com.ytu.store.service.impl;

import com.ytu.store.entity.Supplier;
import com.ytu.store.mapper.SupplierMapper;
import com.ytu.store.service.ISupplierService;
import com.ytu.store.service.ex.PasswordNotMatchException;
import com.ytu.store.service.ex.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements ISupplierService {

    @Autowired
    SupplierMapper supplierMapper;

    @Override
    public Supplier login(String supplierName, String password) {
        // 根据参数username查询用户数据
        Supplier result = supplierMapper.findByUsername(supplierName);
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
