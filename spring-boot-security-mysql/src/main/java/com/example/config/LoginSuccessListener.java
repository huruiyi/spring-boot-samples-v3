package com.example.config;

import jakarta.servlet.http.HttpSession;
import java.time.Instant;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

  @Override
  public void onApplicationEvent(AuthenticationSuccessEvent event) {
    // 1. 获取当前请求
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    if (attributes != null) {
      // 2. 获取当前会话
      HttpSession session = attributes.getRequest().getSession(false);

      if (session != null) {
        // 3. 获取认证信息
        Authentication authentication = event.getAuthentication();
        String username = authentication.getName();
        String sessionId = session.getId();

        // 4. 记录日志（实际中可存入数据库）
        System.out.printf("[登录成功] 用户: %s | SessionID: %s | 时间: %s%n", username, sessionId, Instant.now());

        // 5. 可选的：将 session 信息存入应用上下文
        HashMapSessionRegistry.registerSession(username, sessionId);
      }
    }
  }
}
