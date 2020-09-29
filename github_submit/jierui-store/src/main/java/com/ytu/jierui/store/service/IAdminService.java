package com.ytu.jierui.store.service;

import com.ytu.jierui.store.entity.Admin;

import java.util.List;

public interface IAdminService {

    void create(Admin admin);

    List<Admin> getAllAdminByStatus(Integer status);

    List<Admin> getAllAdmin();

    Admin getAdminByAid(Integer aid);

    Admin getAdminByConditions(Integer aid,String adminname);

    void changeStatus(Integer aid,Integer status);

    void changeBelongerByAid(Integer aid,String belonger);

    void deleteAdmin(Integer aid);

}
