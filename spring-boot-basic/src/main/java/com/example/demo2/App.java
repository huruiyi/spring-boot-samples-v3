package com.example.demo2;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

  /*
    生命周期的配置
   */
  @Test
  public void demo1() {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext2.xml");
    CustomerDAO customerDAO = (CustomerDAO) context.getBean("customerDAO");
    customerDAO.save();
    context.close();
  }

  /*
    Bean的作用范围配置
   */
  @Test
  public void demo2() {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext2.xml");
    CustomerDAO customerDAO1 = (CustomerDAO) context.getBean("customerDAO");
    System.out.println(customerDAO1);
    CustomerDAO customerDAO2 = (CustomerDAO) context.getBean("customerDAO");
    System.out.println(customerDAO2);
    System.out.println(customerDAO1 == customerDAO2);
    context.close();
  }
}
