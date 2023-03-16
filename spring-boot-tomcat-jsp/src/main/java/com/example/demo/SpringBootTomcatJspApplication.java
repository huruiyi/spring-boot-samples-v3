package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootTomcatJspApplication {

	/**
	 * 需运行于tomcat下，或者独立打开spring-boot-tomcat-jsp项目，直接运行
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringBootTomcatJspApplication.class, args);
	}

}
