package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SecurityV2Application implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(SecurityV2Application.class, args);
  }

  @Override
  public void run(String... args) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String strongPassword = bCryptPasswordEncoder.encode("password");
    System.out.println("加密之后数据：\t" + strongPassword);
    boolean result = bCryptPasswordEncoder.matches("password", strongPassword);
    System.out.println("比较结果：\t" + result);
  }

}
