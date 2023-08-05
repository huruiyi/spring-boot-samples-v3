package com.example.demo.web;

import com.example.demo.model.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@RestController
public class HelloController {

  @GetMapping("/test")
  public String test() {
    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = servletRequestAttributes.getRequest();

    UserInfo userInfo = (UserInfo) request.getAttribute("user");
    log.info(userInfo.toString());
    return "test success";
  }
}