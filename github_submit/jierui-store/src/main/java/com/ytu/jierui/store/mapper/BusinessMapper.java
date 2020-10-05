package com.ytu.jierui.store.mapper;

import com.ytu.jierui.store.entity.Business;

public interface BusinessMapper {

    Integer save(Business business);

    Integer updateIdByEmail(String idCard,String email);

    Business selectByCardId(String idCard);

    Business selectByEmail(String email);
}
