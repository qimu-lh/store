package com.ytu.jierui.store.service.impl;

import com.ytu.jierui.store.entity.Admin;
import com.ytu.jierui.store.mapper.AdminMapper;
import com.ytu.jierui.store.service.IAdminService;
import com.ytu.jierui.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void create(Admin admin) {
        String adminname = admin.getAdminname();
        Admin result = adminMapper.findByAdminname(adminname);
        // 判断查询结果是否不为null
        if (result != null) {
            // 是：用户名已存在，不允许注册，抛出UsernameConflictException异常
            throw new UsernameConflictException("用户名(" + adminname + ")已经被占用");
        }
        // 参数user是客户端提交的注册数据，并不包含隐藏数据
        // 执行加密
        String salt = UUID.randomUUID().toString().toUpperCase();
        if ("".equals(admin.getPassword())){
            admin.setPassword("888888");
        }
        String md5Password = getMd5Password(admin.getPassword(), salt);
        // 补全数据：salt
        admin.setSalt(salt);
        // 补全数据：加密后的密码
        admin.setPassword(md5Password);
        admin.setStatus(0);
        admin.setCreatedUser(adminname);
        admin.setCreatedTime(new Date());
        admin.setModifiedUser(adminname);
        admin.setModifiedTime(new Date());

        // 调用userMapper的功能插入用户数据，实现注册，并获取插入数据时的返回值
        Integer rows = adminMapper.createAdmin(admin);
        // 判断以上返回值是否不为1
        if (rows != 1) {
            // 是：插入用户数据失败，抛出InsertException
            throw new InsertException("插入用户数据时出现未知错误");
        }
    }

    @Override
    public List<Admin> getAllAdmin() {
        return adminMapper.findAllAdmin();
    }

    @Override
    public Admin getAdminByAid(Integer aid) {
        return adminMapper.findByAid(aid);
    }

    @Override
    public Admin getAdminByConditions(Integer aid, String adminname, Integer status) {
        Admin result=adminMapper.findByConditions(aid,adminname,status);
        if (result==null){
            throw new UserNotFoundException("符合该条件的管理员不存在！");
        }
        return result;
    }

    @Override
    public void changeStatus(Integer aid,Integer status) {
        Integer rows = adminMapper.updateStatus(aid,status);
        if (rows != 1) {
            // 是：更新失败，抛出UpdateException
            throw new UpdateException("更新数据时出现未知错误");
        }
    }

    @Override
    public void changeBelongerByAid(Integer aid, String belonger) {
        Integer rows=adminMapper.updateBelongerByAid(aid,belonger);
        if (rows != 1) {
            // 是：更新失败，抛出UpdateException
            throw new UpdateException("更新数据时出现未知错误");
        }
    }

    @Override
    public void deleteAdmin(Integer aid) {
        Integer rows=adminMapper.deleteByAid(aid);
        if (rows != 1) {
            throw new DeleteException("删除数据时出现未知错误");
        }
    }

    /**
     * 执行密码加密
     * @param password 原密码
     * @param salt 盐值
     * @return 加密后密码
     */
    private String getMd5Password(String password, String salt) {
        // 加密规则：在原密码的左侧和右侧均拼接盐值，循环加密5次
        String str = salt + password + salt;
        for (int i = 0; i < 5; i++) {
            str = DigestUtils.md5DigestAsHex(str.getBytes()).toUpperCase();
        }
        return str;
    }
}
