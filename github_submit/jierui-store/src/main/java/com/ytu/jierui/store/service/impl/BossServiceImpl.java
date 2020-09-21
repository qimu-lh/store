package com.ytu.jierui.store.service.impl;

import com.ytu.jierui.store.entity.Boss;
import com.ytu.jierui.store.entity.User;
import com.ytu.jierui.store.mapper.BossMapper;
import com.ytu.jierui.store.service.IBossService;
import com.ytu.jierui.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class BossServiceImpl implements IBossService {

    @Autowired
    private BossMapper bossMapper;

    @Override
    public void reg(Boss boss) {
        String bossname = boss.getBossname();
        Boss result = bossMapper.findByBossname(bossname);
        // 判断查询结果是否不为null
        if (result != null) {
            // 是：用户名已存在，不允许注册，抛出UsernameConflictException异常
            throw new UsernameConflictException("用户名(" + bossname + ")已经被占用");
        }
        // 参数user是客户端提交的注册数据，并不包含隐藏数据
        // 执行加密
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(boss.getPassword(), salt);
        // 补全数据：salt
        boss.setSalt(salt);
        // 补全数据：加密后的密码
        boss.setPassword(md5Password);
        boss.setCreatedUser(bossname);
        boss.setCreatedTime(new Date());
        boss.setModifiedUser(bossname);
        boss.setModifiedTime(new Date());

        // 调用userMapper的功能插入用户数据，实现注册，并获取插入数据时的返回值
        Integer rows = bossMapper.save(boss);
        // 判断以上返回值是否不为1
        if (rows != 1) {
            // 是：插入用户数据失败，抛出InsertException
            throw new InsertException("插入用户数据时出现未知错误");
        }
    }

    @Override
    public Boss login(String bossname, String password) {
        // 根据参数username查询用户数据
        Boss result = bossMapper.findByBossname(bossname);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：用户名不存在，抛出UserNotFoundException
            throw new UserNotFoundException("管理员不存在");
        }

        // 从查询结果中取出盐值
        String salt = result.getSalt();
        // 调用getMd5Password()，基于参数password和盐值进行加密
        String md5Password = getMd5Password(password, salt);
        // 判断加密后的密码与查询结果中的密码是否不匹配
        if (!result.getPassword().equals(md5Password)) {
            // 是：密码错误，抛出PasswordNotMatchException
            throw new PasswordNotMatchException("密码错误");
        }

        // 将查询结果中不应该返回的字段设置为null
        result.setCreatedUser(null);
        result.setCreatedTime(null);
        result.setModifiedUser(null);
        result.setModifiedTime(null);
        result.setPassword(null);
        result.setSalt(null);
        // 返回查询结果
        return result;
    }

    @Override
    public void changePassword(Integer bid, String bossname, String oldPassword, String newPassword) {
        Boss result = bossMapper.findByBossname(bossname);
        if (result == null) {
            throw new UserNotFoundException("用户数据不存在");
        }
        // 从查询结果中取出盐值
        String salt = result.getSalt();
        // 将参数oldPassword进行加密，得到oldMd5Password
        String oldMd5Password = getMd5Password(oldPassword, salt);
        // 判断查询结果中的password与oldMd5Password是否不匹配
        if (!result.getPassword().equals(oldMd5Password)) {
            // 是：密码验证失败，抛出PasswordNotMatchException
            throw new PasswordNotMatchException("原密码错误");
        }
        // 将参数newPassword进行加密，得到newMd5Password
        String newMd5Password = getMd5Password(newPassword, salt);
        // 执行更新密码，获取返回值
        Integer rows = bossMapper.updatePassword(bid, newMd5Password, bossname, new Date());
        // 判断返回的受影响的行数是否不为1
        if (rows != 1) {
            // 是：更新失败，抛出UpdateException
            throw new UpdateException("更新数据时出现未知错误");
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
