package com.ytu.jierui.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.ytu.jierui.store.mapper")
@SpringBootApplication
public class JieruiStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(JieruiStoreApplication.class, args);
    }

}
