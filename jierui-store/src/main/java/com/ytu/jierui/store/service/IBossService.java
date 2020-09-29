package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.Authority;
import com.ytu.jierui.store.entity.Boss;

import java.util.Date;

public interface IBossService {

    void reg(Boss boss);

    Boss login(String bossname, String password);

    Authority getAuthorityByBid(Integer bid);

    void changePassword(Integer bid, String bossname, String oldPassword, String newPassword);

}
