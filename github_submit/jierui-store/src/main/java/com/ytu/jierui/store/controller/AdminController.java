package com.ytu.jierui.store.controller;

import com.ytu.jierui.store.entity.Admin;
import com.ytu.jierui.store.service.IAdminService;
import com.ytu.jierui.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admins")
public class AdminController extends BaseController{

    @Autowired
    private IAdminService adminService;

    @RequestMapping("create")
    public JsonResult<Void> create(Admin admin) {
        adminService.create(admin);
        return new JsonResult<>(SUCCESS);
    }

    @GetMapping("/")
    public JsonResult<List<Admin>> getAll(){
        List<Admin> allAdmin = adminService.getAllAdmin();
        return new JsonResult<>(SUCCESS,allAdmin);
    }

    @GetMapping("{aid}/findAdmin")
    public JsonResult<Admin> getAdmin(@PathVariable("aid") Integer aid){
        Admin data=adminService.getAdminByAid(aid);
        return new JsonResult<>(SUCCESS,data);
    }

    @GetMapping("{aid}/findAdminByCondition/{adminname}/{status}")
    public JsonResult<Admin> getAdminByCondition(@PathVariable("aid") Integer aid,
                                      @PathVariable("adminname") String adminname,
                                      @PathVariable("status") Integer status){
        Admin data=adminService.getAdminByConditions(aid,adminname,status);
        return new JsonResult<>(SUCCESS,data);
    }

    @RequestMapping("{aid}/changeStatus/{status}")
    public JsonResult<Void> changeStatusByAid(@PathVariable("aid") Integer aid,
                                              @PathVariable("status") Integer status){
        adminService.changeStatus(aid,status);
        // 返回成功，及操作结果
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("{aid}/changeBelonger/{belonger}")
    public JsonResult<Void> changeBelonger(@PathVariable("aid") Integer aid,
                                              @PathVariable("belonger") String belonger){
        adminService.changeBelongerByAid(aid,belonger);
        // 返回成功，及操作结果
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("{aid}/delete_admin")
    public JsonResult<Void> deleteAdminByAid(@PathVariable("aid") Integer aid) {
        adminService.deleteAdmin(aid);
        // 返回成功，及操作结果
        return new JsonResult<>(SUCCESS);
    }
}
