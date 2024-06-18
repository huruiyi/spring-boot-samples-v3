package com.example.service;

import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextService implements ApplicationContextAware {

  ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  public ApplicationContext getApplicationContext() {
    return this.applicationContext;
  }

}
