package com.example.demo3;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean的实例化的方式
 */
public class App {

  @Test
  /*
   * 无参数构造方法
   */
  public void demo1() {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext3.xml");
    Bean1 bean1 = (Bean1) context.getBean("bean1");
    System.out.println(bean1);

    context.close();
  }

  @Test
  /*
   * 静态工厂实例化
   */
  public void demo2() {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext3.xml");
    Bean2 bean2 = (Bean2) context.getBean("bean2");
    System.out.println(bean2);

    context.close();
  }

  @Test
  /*
   * 实例工厂实例化
   */
  public void demo3() {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext3.xml");
    Bean3 bean3 = (Bean3) context.getBean("bean3");
    System.out.println(bean3);

    context.close();
  }
}
