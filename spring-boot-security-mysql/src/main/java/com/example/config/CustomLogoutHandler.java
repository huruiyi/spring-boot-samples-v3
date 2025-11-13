package com.example.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    if (authentication != null) {
      String username = authentication.getName();
      // 例如：清除 Redis 中的在线用户、更新数据库状态等
      System.out.println("CustomLogoutHandler，" + username + " 登出成功");
    }
  }
}
