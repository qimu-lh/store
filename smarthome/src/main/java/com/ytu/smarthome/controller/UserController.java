package com.ytu.smarthome.controller;

import com.ytu.smarthome.entity.User;
import com.ytu.smarthome.service.IUserService;
import com.ytu.smarthome.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    //用户注册
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        //执行注册
        userService.reg(user);
        //相应操作成功
        return new JsonResult<>(SUCCESS);
    }

    //用户登录
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        // 执行登录，获取登录返回结果
        User data = userService.login(username, password);
        // 向Session中封装数据
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        // 向客户端响应操作成功
        return new JsonResult<>(SUCCESS, data);
    }

    //显示用户数据
    @GetMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        // 从session中获取uid
        Integer uid = getUidFromSession(session);
        // 查询匹配的数据
        User data = userService.getByUid(uid);
        // 响应
        return new JsonResult<>(SUCCESS, data);
    }

}
