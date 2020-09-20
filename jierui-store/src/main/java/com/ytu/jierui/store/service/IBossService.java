package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.Boss;

public interface IBossService {

    void reg(Boss boss);

    Boss login(String bossname, String password);

    void changePassword(Integer bid, String bossname, String oldPassword, String newPassword);
}
