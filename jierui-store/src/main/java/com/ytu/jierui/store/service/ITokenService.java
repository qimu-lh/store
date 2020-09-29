package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.Token;
import com.ytu.jierui.store.entity.User;

import java.util.Date;

public interface ITokenService {

    void addToken(Integer uid, String autoLoginToken, String tokenType, Date overdueTime);

    void deleteByUid(Integer uid);

    Token findTokenByTokenName(String autoLoginToken);

    User findUserByToken(String autoLoginToken);
}
