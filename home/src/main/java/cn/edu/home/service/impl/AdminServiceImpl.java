package cn.edu.home.service.impl;

import cn.edu.home.entity.Admin;
import cn.edu.home.mapper.AdminMapper;
import cn.edu.home.service.IAdminService;
import cn.edu.home.service.ex.PasswordNotMatchException;
import cn.edu.home.service.ex.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements IAdminService {
    //添加持久层对象
    @Autowired
    private AdminMapper adminMapper;


    @Override
    public Admin AdminLogin(String adminname, String password) {
        // 根据参数adminname查询用户数据
        Admin result = adminMapper.findByAdminname(adminname);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：用户名不存在，抛出UserNotFoundException
            throw new UserNotFoundException("管理员不存在");
        }
        // 判断数据库密码与查询结果中的密码是否不匹配
        if (!result.getPassword().equals(password)) {
            // 是：密码错误，抛出PasswordNotMatchException
            throw new PasswordNotMatchException("密码错误");
        }

        // 将查询结果中不应该返回的字段设置为null
        // 例如：password
        result.setPassword(null);
        // 返回查询结果
        return result;
    }
}

