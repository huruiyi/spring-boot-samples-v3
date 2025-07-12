package com.example.web;

import com.example.service.UserSessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user")
public class UserController {


  @Autowired
  UserSessionService userSessionService;

  @RequestMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/logout")
  public String performLogout() {
    return "redirect:/user/login";
  }

  private void invalidateCookie(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate(); // 使当前 session 失效
    }
    Cookie cookie = new Cookie("rm", null);
    cookie.setMaxAge(0); // 立即过期
    cookie.setPath(getCookiePath(request));
    cookie.setSecure(request.isSecure()); // 保持与原 Cookie 一致
    response.addCookie(cookie);
  }

  private String getCookiePath(HttpServletRequest request) {
    String contextPath = request.getContextPath();
    return !contextPath.isEmpty() ? contextPath : "/";
  }

  @ResponseBody
  @GetMapping("/offline")
  public String offline(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
    userSessionService.logoutUserByUsername(authentication.getName());
    invalidateCookie(request, response);
    return "logout success";
  }

}
