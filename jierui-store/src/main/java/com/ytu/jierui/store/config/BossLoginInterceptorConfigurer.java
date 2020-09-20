package com.ytu.jierui.store.config;

import com.ytu.jierui.store.interceptor.BossLoginInterceptor;
import com.ytu.jierui.store.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 老板登录拦截器的配置类
 */
/*
@Configuration
public class BossLoginInterceptorConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor bossLoginInterceptor = new BossLoginInterceptor();

        // 创建白名单
        List<String> excludePaths = new ArrayList<>();
        //CSS
        excludePaths.add("/static/assets/**");
        excludePaths.add("/static/bootstrap-3.3.7-dist/**");
        //老板界面
        excludePaths.add("/static/web/dashboard.html");
        excludePaths.add("/static/web/manage-login.html");
        //用户界面
        excludePaths.add("/static/web/account-login.html");
        excludePaths.add("/static/web/account-register.html");
        excludePaths.add("/templates/index.html");
        excludePaths.add("/templates/shop-single.html");
        //老板请求
        excludePaths.add("/bosses/login");
        //用户请求
        excludePaths.add("/users/reg");
        excludePaths.add("/users/login");
        excludePaths.add("/products/**");

        // 注册拦截器，并设置黑白名单
        registry.addInterceptor(bossLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePaths);
    }
}
*/
