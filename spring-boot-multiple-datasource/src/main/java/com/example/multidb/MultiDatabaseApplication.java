package com.example.multidb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 多数据源动态切换主应用类
 */
@SpringBootApplication
public class MultiDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiDatabaseApplication.class, args);
        System.out.println("============================================");
        System.out.println("Spring Boot 多数据源动态切换应用启动成功！");
        System.out.println("访问地址: http://localhost:8080");
        System.out.println("============================================");
    }
}
