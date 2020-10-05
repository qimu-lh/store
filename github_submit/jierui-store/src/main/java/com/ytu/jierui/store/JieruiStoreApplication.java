package com.ytu.jierui.store;

import com.ytu.jierui.store.interceptor.UserLoginInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Import(UserLoginInterceptor.class)
@MapperScan("com.ytu.jierui.store.mapper")
@SpringBootApplication
@Configuration
public class JieruiStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(JieruiStoreApplication.class, args);
    }

    @Bean
    public MultipartConfigElement getMultipartConfig() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        DataSize maxFileSize = DataSize.ofMegabytes(50);
        factory.setMaxFileSize(maxFileSize);
        DataSize maxRequestSize = DataSize.ofMegabytes(100);
        factory.setMaxRequestSize(maxRequestSize);

        return factory.createMultipartConfig();
    }
}
