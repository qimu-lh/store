package com.ytu.jierui.store.mapper;

import com.ytu.jierui.store.entity.Token;
import com.ytu.jierui.store.entity.User;

import java.util.Date;

public interface TokenMapper {

    Integer saveToken(Integer uid, String autoLoginToken, String tokenType, Date overdueTime);

    Integer deleteTokenByUid(Integer uid);

    Token selectTokenByTokenName(String autoLoginToken);

    User selectUserByToken(Integer uid,String autoLoginToken);
}

