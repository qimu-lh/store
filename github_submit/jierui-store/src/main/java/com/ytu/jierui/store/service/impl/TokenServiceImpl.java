package com.ytu.jierui.store.service.impl;

import com.ytu.jierui.store.entity.Token;
import com.ytu.jierui.store.entity.User;
import com.ytu.jierui.store.mapper.TokenMapper;
import com.ytu.jierui.store.service.ITokenService;
import com.ytu.jierui.store.service.ex.DeleteException;
import com.ytu.jierui.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements ITokenService {

    @Autowired
    TokenMapper tokenMapper;

    @Override
    public void addToken(Integer uid, String autoLoginToken, String tokenType, Date overdueTime) {
        Integer rows=tokenMapper.saveToken(uid,autoLoginToken,tokenType,overdueTime);
        // 判断以上返回值是否不为1
        if (rows != 1) {
            // 是：插入用户数据失败，抛出InsertException
            throw new InsertException("插入数据时出现未知错误");
        }
    }

    @Override
    public void deleteByUid(Integer uid) {
        Integer rows = tokenMapper.deleteTokenByUid(uid);
        if (rows!=1){
            throw new DeleteException("删除数据时出现异常！");
        }
    }

    @Override
    public Token findTokenByTokenName(String autoLoginToken) {
        return tokenMapper.selectTokenByTokenName(autoLoginToken);
    }

    @Override
    public User findUserByToken(String autoLoginToken) {
        Token result=tokenMapper.selectTokenByTokenName(autoLoginToken);
        Integer uid=result.getUid();
        return tokenMapper.selectUserByToken(uid,autoLoginToken);
    }
}
