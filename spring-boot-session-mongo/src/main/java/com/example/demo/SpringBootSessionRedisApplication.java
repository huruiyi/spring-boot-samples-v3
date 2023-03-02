package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

@SpringBootApplication
@EnableMongoHttpSession
public class SpringBootSessionRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSessionRedisApplication.class, args);
	}

}
