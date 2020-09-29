package com.ytu.jierui.store.controller;

import com.ytu.jierui.store.service.ex.*;
import com.ytu.jierui.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 控制器类的基类
 */
public class BaseController {

    /**
     * 操作结果的“成功”状态
     */
    public static final Integer SUCCESS = 2000;

    /**
     * 操作结果的“错误”状态
     */
    public static final Integer ERROR = 4444;

    /**
     * 从Session中获取当前登录的用户的uid
     * @param session Session对象
     * @return 当前登录的用户的id
     */
    protected Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 从Session中获取当前登录的管理员用户的bid
     * @param session Session对象
     * @return 当前登录的用户的id
     */
    protected Integer getBidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("bid").toString());
    }

    /**
     * 从Session中获取当前登录的用户名
     * @param session Session对象
     * @return 当前登录的用户名
     */
    protected String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }

    /**
     * 从Session中获取当前登录的管理员名
     * @param session Session对象
     * @return 当前登录的管理名
     */
    protected String getBossnameFromSession(HttpSession session) {
        return session.getAttribute("bossname").toString();
    }

    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handlerException(Throwable e) {
        JsonResult<Void> jr = new JsonResult<>();
        jr.setMessage(e.getMessage());

        if (e instanceof UsernameConflictException) {
            // 4000-用户名冲突异常，例如注册时用户名已经被占用
            jr.setState(4000);
        } else if (e instanceof UserNotFoundException) {
            // 4001-用户数据不存在
            jr.setState(4001);
        }else if (e instanceof PasswordNotMatchException) {
            // 4002-验证密码失败
            jr.setState(4002);
        }else if (e instanceof AccessDeniedException) {
            // 4003-拒绝访问，可能因为尝试访问他人的数据
            jr.setState(4003);
        } else if (e instanceof CartNotFoundException) {
            // 4004-购物车数据不存在
            jr.setState(4004);
        }else if (e instanceof ProductConflictException) {
            // 4005-商品已存在
            jr.setState(4005);
        }else if (e instanceof ProductNotFoundException) {
            // 4006-商品未找到
            jr.setState(4006);
        }else if (e instanceof InsertException) {
            // 5000-插入数据异常
            jr.setState(5000);
        }else if (e instanceof UpdateException) {
            // 5001-更新数据异常
            jr.setState(5001);
        } else if (e instanceof DeleteException) {
            // 5002-删除数据异常
            jr.setState(5002);
        }

        return jr;
    }
}

