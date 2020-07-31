package com.ytu.store.mapper;

import com.ytu.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserMapper {

    Integer save(User user);

    Integer updatePassword(
            @Param("uid") Integer uid,
            @Param("password") String password,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

    Integer resetPasswordByEmail(Integer code,String email,String password);

    Integer updateInfo(User user);

    Integer updateCode(Integer code,String email);

    User findByUsername(String username);

    User findByUid(Integer uid);

    User findByEmail(String email);

    List<User> findUsers();
}
