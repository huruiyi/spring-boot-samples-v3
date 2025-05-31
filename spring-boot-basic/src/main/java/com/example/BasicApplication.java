package com.example;

import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BasicApplication {

  public static void main(String[] args) {
    try (var ctx = SpringApplication.run(BasicApplication.class, args)) {
      System.out.println("# Beans: " + ctx.getBeanDefinitionCount());
      var names = ctx.getBeanDefinitionNames();
      Arrays.sort(names);
      Arrays.asList(names).forEach(System.out::println);
    }
  }

}
