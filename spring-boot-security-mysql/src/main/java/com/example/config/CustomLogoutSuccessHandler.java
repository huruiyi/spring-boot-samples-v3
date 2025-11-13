package com.example.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    String username = authentication != null ? authentication.getName() : "anonymous";
    System.out.println("CustomLogoutSuccessHandler,用户" + username + " 登出成功");

    // 发布 LogoutSuccessEvent 是 Spring Security 自动做的，无需手动发布
    // 只要走的是标准注销流程，事件就会触发！

    // 重定向到自定义页面
    response.sendRedirect("/user/login");
  }
}
