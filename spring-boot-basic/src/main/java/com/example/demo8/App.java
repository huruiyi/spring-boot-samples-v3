package com.example.demo8;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

  @Test
  public void demo1() {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext8.xml");
    ProductService productService = (ProductService) context.getBean("productService");
    productService.save();

    context.close();
  }

  @Test
  public void demo2() {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext8.xml");
    OrderService service = (OrderService) context.getBean("orderService");
    service.save();

    context.close();
  }
}
