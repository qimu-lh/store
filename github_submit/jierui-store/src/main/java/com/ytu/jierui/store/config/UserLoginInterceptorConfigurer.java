package com.ytu.jierui.store.config;

import com.ytu.jierui.store.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户登录拦截器的配置类
 */
@Configuration
public class UserLoginInterceptorConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor userLoginInterceptor = new UserLoginInterceptor();

        // 创建白名单
        List<String> excludePaths = new ArrayList<>();
        //CSS
        excludePaths.add("/static/assets/**");
        excludePaths.add("/static/bootstrap-3.3.7-dist/**");
        //老板界面
        //excludePaths.add("/static/web/dashboard.html");
        excludePaths.add("/static/web/manage-login.html");
        excludePaths.add("/static/web/show.html");
        //用户界面
        excludePaths.add("/static/web/account-login.html");
        excludePaths.add("/static/web/account-register.html");
        excludePaths.add("/templates/index.html");
        excludePaths.add("/templates/shop-single.html");
        excludePaths.add("/static/web/search-results.html");
        //商家界面
        excludePaths.add("/static/web/shop-register.html");
        excludePaths.add("/static/web/shop-order.html");
        //老板请求
        excludePaths.add("/bosses/login");
        //用户请求
        excludePaths.add("/users/reg");
        excludePaths.add("/users/login");
        excludePaths.add("/products/**");
        //商家请求
        excludePaths.add("/businesses/**");
        excludePaths.add("/orders/**");

        // 注册拦截器，并设置黑白名单
        registry.addInterceptor(userLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePaths);
    }
}
