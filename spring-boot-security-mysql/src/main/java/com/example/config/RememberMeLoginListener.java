package com.example.config;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class RememberMeLoginListener {

  @EventListener
  public void onInteractiveAuthenticationSuccess(InteractiveAuthenticationSuccessEvent event) {
    if (event.getAuthentication() instanceof RememberMeAuthenticationToken) {
      // 处理通过 Remember-Me 自动登录的情况
      System.out.println("用户通过 Remember-Me 自动登录: " + event.getAuthentication().getName());
    }
  }
}
