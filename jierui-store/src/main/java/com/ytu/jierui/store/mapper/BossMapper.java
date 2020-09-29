package com.ytu.jierui.store.mapper;


import com.ytu.jierui.store.entity.Authority;
import com.ytu.jierui.store.entity.Boss;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface BossMapper {

    //注册
    Integer save(Boss boss);

    //根据用户名查询
    Boss findByBossname(String bossname);

    //根据bid查询权限
    Authority findAuthorityByBid(Integer bid);

    //更新锁定时的三个信息
    Integer updateLock(@Param("bossname") String bossname,
                       @Param("lockNum")Integer lockNum,
                       @Param("lockTime")Date lockTime,
                       @Param("unlockTime")Date unlockTime);

    //修改密码
    Integer updatePassword(
            @Param("bid") Integer bid,
            @Param("password") String password,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

}
