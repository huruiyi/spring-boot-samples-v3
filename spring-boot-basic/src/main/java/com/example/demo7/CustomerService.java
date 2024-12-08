package com.example.demo7;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


/*<bean id="customerDAO" class="com.example.demo2.CustomerDAOImpl"
	scope="prototype" 
	init-method="setup" 
	destroy-method="destroy" />*/

@Service("customerService")
/* @Scope 默认单例 singleton */
/* prototype 多例 */
@Scope("singleton")
public class CustomerService {

  @PostConstruct
  public void init() {
    System.out.println("init......");
  }

  public void save() {
    System.out.println("save......");
  }

  @PreDestroy
  public void destroy() {
    System.out.println("destroy......");
  }
}
