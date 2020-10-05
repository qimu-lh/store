package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.Business;

public interface IBusinessService {

    void reg(Business business);

    void updateId(String idCard,String email);
}
