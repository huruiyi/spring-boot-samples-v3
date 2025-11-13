package com.example.config;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionLifecycleListener {

  @EventListener
  public void onSessionCreated(HttpSessionCreatedEvent event) {
    System.out.println("会话创建: " + event.getSession().getId());
  }

  @EventListener
  public void onSessionDestroyed(HttpSessionDestroyedEvent event) {
    System.out.println("会话销毁: " + event.getId());
  }

  // 添加安全会话销毁事件监听
  @EventListener
  public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
    // 认证成功时的处理
    User user = (User) event.getAuthentication().getPrincipal();
    System.out.println("用户认证成功: " + user.getUsername());
  }

  @EventListener
  public void onLogoutSuccess(LogoutSuccessEvent event) {
    // 明确的登出事件监听
    User user = (User) event.getAuthentication().getPrincipal();
    System.out.println("用户登出成功: " + user.getUsername());
  }

}
