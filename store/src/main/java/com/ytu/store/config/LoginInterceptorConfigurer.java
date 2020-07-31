package com.ytu.store.config;

import com.ytu.store.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录拦截器的配置类
 */
@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor userLoginInterceptor = new UserLoginInterceptor();

        // 创建白名单
        List<String> excludePaths = new ArrayList<>();
        excludePaths.add("/assets/**");

        excludePaths.add("/web/account-login.html");
        excludePaths.add("/web/account-password-recovery.html");
        excludePaths.add("/web/index.html");
        excludePaths.add("/web/operator-login.html");
        excludePaths.add("/web/supplier-login.html");
        excludePaths.add("/web/shop-single.html");
        excludePaths.add("/web/search-results.html");

        excludePaths.add("/users/reg");
        excludePaths.add("/users/login");
        excludePaths.add("/users/get_code");
        excludePaths.add("/users/reset_password");
        excludePaths.add("/districts/**");
        excludePaths.add("/products/**");
        excludePaths.add("/operators/login");
        excludePaths.add("/suppliers/login");

        // 注册拦截器，并设置黑白名单
        registry.addInterceptor(userLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePaths);
    }
}
