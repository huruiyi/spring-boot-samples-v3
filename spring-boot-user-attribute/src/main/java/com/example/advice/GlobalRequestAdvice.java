package com.example.advice;

import com.example.model.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@ControllerAdvice
public class GlobalRequestAdvice {

  @ModelAttribute
  public void authenticationUser(HttpServletRequest request) {

    Iterator<String> names = request.getHeaderNames().asIterator();
    while (names.hasNext()) {
      String name = names.next();
      log.info(request.getHeader(name));
    }
    // 此处实际应该根据header的token解析出用户
    // 本处为了简单，直接虚构一个用户
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(3L);
    userInfo.setUserName("Tony");

    request.setAttribute("user", userInfo);
  }
}
