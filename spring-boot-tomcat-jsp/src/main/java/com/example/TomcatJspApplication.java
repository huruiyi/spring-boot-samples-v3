package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TomcatJspApplication {

  /**
   * 需运行于tomcat下，或者独立打开spring-boot-tomcat-jsp项目，直接运行
   */
  public static void main(String[] args) {
    SpringApplication.run(TomcatJspApplication.class, args);
  }

}
