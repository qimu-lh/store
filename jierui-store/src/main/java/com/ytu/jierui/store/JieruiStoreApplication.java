package com.ytu.jierui.store;

import com.ytu.jierui.store.interceptor.UserLoginInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(UserLoginInterceptor.class)
@MapperScan("com.ytu.jierui.store.mapper")
@SpringBootApplication
public class JieruiStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(JieruiStoreApplication.class, args);
    }

}
