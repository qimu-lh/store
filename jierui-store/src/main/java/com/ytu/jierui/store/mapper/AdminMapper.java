package com.ytu.jierui.store.mapper;

import com.ytu.jierui.store.entity.Admin;

import java.util.List;

public interface AdminMapper {

    //创建管理员
    Integer createAdmin(Admin admin);

    Admin findByAdminname(String adminname);

    Admin findByAid(Integer aid);

    Admin findByConditions(Integer aid,String adminname);

    List<Admin> findAllByStatus(Integer status);

    List<Admin> findAllAdmin();

    Integer updateStatus(Integer aid,Integer status);

    Integer updateBelongerByAid(Integer aid,String belonger);

    Integer deleteByAid(Integer aid);
}
