package com.example.demo;

import com.example.demo.service.ApplicationContextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringBootUserTlApplication implements CommandLineRunner {

  @Autowired
  ApplicationContextService contextService;

  public static void main(String[] args) {
    SpringApplication.run(SpringBootUserTlApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    String[] beanDefinitionNames = contextService.getApplicationContext().getBeanDefinitionNames();
    log.info("---------------------------------------------------------------------------------------------------------------");
    for (String name : beanDefinitionNames) {
      log.info(name);
    }
    log.info("---------------------------------------------------------------------------------------------------------------");
  }
}
