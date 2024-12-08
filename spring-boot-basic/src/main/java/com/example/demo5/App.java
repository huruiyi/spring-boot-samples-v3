package com.example.demo5;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 集合类型的属性注入
 */
public class App {

  @Test
  public void demo1() {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext5.xml");

    CollectionBean collectionBean = (CollectionBean) context.getBean("collectionBean");
    System.out.println(collectionBean);

    context.close();
  }
}
