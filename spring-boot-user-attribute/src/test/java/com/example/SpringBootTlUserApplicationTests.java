package com.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class SpringBootTlUserApplicationTests {

  @Test
  void contextLoads() {
    String message = "hello world!";
    log.info(message);
    Assertions.assertEquals("hello world!", message);
  }

}
