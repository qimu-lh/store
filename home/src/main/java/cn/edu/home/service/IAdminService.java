package cn.edu.home.service;

import cn.edu.home.entity.Admin;

public interface IAdminService {
    //管理员登录功能
    Admin AdminLogin(String adminname, String password);
}

