package com.example.config;

import org.springframework.context.event.EventListener;
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

}
