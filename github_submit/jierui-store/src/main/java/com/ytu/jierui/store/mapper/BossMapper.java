package com.ytu.jierui.store.mapper;


import com.ytu.jierui.store.entity.Boss;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface BossMapper {

    //注册
    Integer save(Boss boss);

    //登录
    Boss findByBossname(String bossname);

    //修改密码
    Integer updatePassword(
            @Param("bid") Integer bid,
            @Param("password") String password,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);
}
