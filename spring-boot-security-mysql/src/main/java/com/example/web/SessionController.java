package com.example.web;

import jakarta.servlet.http.HttpSession;
import java.time.Instant;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {


  @GetMapping("/session-info")
  public String sessionInfo(Authentication authentication, HttpSession session) {
    return String.format(
        "用户名: %s <br> Session ID: %s <br> 创建时间: %s",
        authentication.getName(),
        session.getId(),
        Instant.ofEpochMilli(session.getCreationTime())
    );
  }

  @GetMapping("/my-session")
  public String getCurrentSession(HttpSession session) {
    return "当前 Session ID: " + session.getId();
  }

  // 获取当前会话ID
  @GetMapping("/current-session")
  public String getCurrentSessionId(HttpSession session) {
    return session.getId();
  }

}
