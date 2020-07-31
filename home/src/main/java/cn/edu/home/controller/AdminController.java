package cn.edu.home.controller;

import cn.edu.home.entity.Admin;
import cn.edu.home.entity.User;
import cn.edu.home.service.IAdminService;
import cn.edu.home.service.IUserService;
import cn.edu.home.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IAdminService adminService;

    //管理员登录
    @RequestMapping("login")
    public JsonResult<Admin> adminLogin(String adminname, String password, HttpSession session) {
        // 执行登录，获取登录返回结果
        Admin data = adminService.AdminLogin(adminname, password);
        // 向Session中封装数据
        session.setAttribute("aid", data.getAid());
        session.setAttribute("adminname", data.getAdminname());
        // 向客户端响应操作成功
        return new JsonResult<>(SUCCESS, data);
    }

    @GetMapping("/")
    public JsonResult<List<User>> getByUidAdmin() {
        // 调用业务层对象查询数据
        List<User> data = userService.getUsers();
        // 响应成功，及查询到的数据
        return new JsonResult<>(SUCCESS, data);
    }
    /**
     * 根据id删除用户
     *
     * @param uid 用户id
     * @return json结果
     */
    @RequestMapping("{uid}/delete")
    public JsonResult<Void> delete(@PathVariable("uid") Integer uid) {
        // 执行删除
        userService.delete(uid);
        // 响应成功
        return new JsonResult<>(SUCCESS);
    }
}

