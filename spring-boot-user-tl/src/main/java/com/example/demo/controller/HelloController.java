package com.example.demo.controller;

import com.example.demo.model.UserInfo;
import com.example.demo.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {

  @GetMapping("/test")
  public String test() {
    UserInfo currentUser = UserThreadLocal.getCurrentUser();
    log.info(currentUser.toString());
    return "test success";
  }
}