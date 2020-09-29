package com.ytu.jierui.store.interceptor;

import com.ytu.jierui.store.entity.Token;
import com.ytu.jierui.store.entity.User;
import com.ytu.jierui.store.service.ITokenService;
import com.ytu.jierui.store.service.IUserService;
import com.ytu.jierui.store.util.CookieUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class UserLoginInterceptor implements HandlerInterceptor, ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("----自动登录----");
        //获取到令牌
        Cookie c= CookieUtil.getCookieByCookieName(request.getCookies(),"altoken");
        if (c!=null){
            //判断令牌
            ITokenService tokenService = (ITokenService) UserLoginInterceptor.getBean(ITokenService.class);
            Token altoken=tokenService.findTokenByTokenName(c.getValue());
            //令牌是否有效，true无效，false有效
            boolean f=(altoken==null)
                    || (altoken.getOverdueTime().getTime()<new Date().getTime())
                    || !"autoLogin".equals(altoken.getTokenType());
            if (f){
                return true;
            }
            //根据令牌获取用户信息
            User userInfo=tokenService.findUserByToken(altoken.getAutoLoginToken());
            //将用户信息放入session
            request.getSession().setAttribute("userInfo",userInfo);
        }
        return true;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (UserLoginInterceptor.applicationContext == null) {
            UserLoginInterceptor.applicationContext = applicationContext;
            System.out.println(
                    "========ApplicationContext配置成功,在普通类可以通过调用ToolSpring.getAppContext()获取applicationContext对象,applicationContext="
                            + applicationContext + "========");
        }
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
}
