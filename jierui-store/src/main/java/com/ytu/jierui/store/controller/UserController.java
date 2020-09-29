package com.ytu.jierui.store.controller;

import com.ytu.jierui.store.entity.User;
import com.ytu.jierui.store.service.ITokenService;
import com.ytu.jierui.store.service.IUserService;
import com.ytu.jierui.store.util.JsonResult;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

/**
 * 处理用户数据相关请求的控制器类
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ITokenService tokenService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password,
                                  boolean status, HttpSession session,
                                  HttpServletResponse response) {
        User data = userService.login(username, password);
        //判断isRemember的值
        if (status){
            //生成令牌（唯一性）
            String autoLoginToken= UUID.randomUUID().toString();
            //将令牌存入数据库
            tokenService.addToken(data.getUid(),autoLoginToken,"autoLogin",new Date(new Date().getTime()+1000L*60*60*24*365));
            //将cookie返回给前端
            Cookie c=new Cookie("altoken",autoLoginToken);
            c.setMaxAge(60*60*24*365);
            //设置cookie的添加路径
            c.setPath("/");
            response.addCookie(c);
        }
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        return new JsonResult<User>(SUCCESS, data);
    }

    @RequestMapping("logout")
    public JsonResult<Void> cancelLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        //删除数据库中的cookie信息
        tokenService.deleteByUid(getUidFromSession(session));
        // 删除session里面的用户信息
        session.removeAttribute("username");
        session.removeAttribute("uid");
        session.removeAttribute("userInfo");
        // 保存cookie，实现自动登录
        Cookie cookie = new Cookie("altoken", "");
        // 设置cookie的持久化时间，0
        cookie.setMaxAge(0);
        // 设置为当前项目下都携带这个cookie
        cookie.setPath("/");
        // 向客户端发送cookie
        response.addCookie(cookie);
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("info")
    public JsonResult<User> getUsername(HttpSession session){
        User data = (User)session.getAttribute("userInfo");
        if (data!=null){
            return new JsonResult<>(SUCCESS,data);
        }else {
            return new JsonResult<>(ERROR);
        }
    }



    @RequestMapping("cookie")
    public JsonResult<User> rememberPwd(@RequestParam(required = false, defaultValue = "0") int state,
                                        String username, String password,
                                        HttpServletResponse response,
                                        HttpServletRequest request) {
        User data = userService.login(username, password);
        //如果选择记住密码,则创建cookie,并将账号密码注入cookie
        if (state == 1) {
            //创建cookie对象
            Cookie ck = new Cookie(username, password);
            //设置Cookie有效时间,单位为妙
            ck.setMaxAge(60 * 60 * 24);
            //设置Cookie的有效范围,/为全部路径
            ck.setPath("/");
            response.addCookie(ck);
        } else {
            //如果没有选中记住密码,则将已记住密码的cookie失效.即有效时间设为0
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(username)) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        return new JsonResult<User>(SUCCESS, data);
    }
}
