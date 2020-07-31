package com.ytu.store.controller;

import com.ytu.store.entity.User;
import com.ytu.store.service.IEmailService;
import com.ytu.store.service.IUserService;
import com.ytu.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 处理用户数据相关请求的控制器类
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController{

    @Autowired
    private IUserService userService;
    @Autowired
    private IEmailService emailService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User data = userService.login(username, password);
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        return new JsonResult<User>(SUCCESS , data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(
            @RequestParam("old_password") String oldPassword,
            @RequestParam("new_password") String newPassword, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(SUCCESS);
    }

    @GetMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        User data = userService.getByUid(uid);
        return new JsonResult<>(SUCCESS, data);
    }

    @PostMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("get_code")
    public JsonResult<Void> getCode(
            @RequestParam("email") String email) {
        emailService.sendCodeEmail(email);
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("reset_password")
    public JsonResult<Void> resetPasswordBingo(
            @RequestParam("code") String code,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        userService.resetPassword(Integer.parseInt(code),email,password);
        return new JsonResult<>(SUCCESS);
    }
}
