package com.example.demo1;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Spring的入门
 */
public class App {

  @Test
  /*
    传统方式的调用
   */ public void demo1() {
    UserDAOImpl userDAO = new UserDAOImpl();
    userDAO.setName("王东");
    userDAO.save();
  }

  @Test
  /*
    Spring的方式的调用
   */ public void demo2() {
    // 创建Spring的工厂
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext1.xml");
    UserDAO userDAO = (UserDAO) context.getBean("userDAO");
    userDAO.save();

    context.close();
  }

  @Test
  /*
    加载磁盘上的配置文件
   */ public void demo3() {
    FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("C:\\applicationContext.xml");
    UserDAO userDAO = (UserDAO) context.getBean("userDAO");
    userDAO.save();

    context.close();
  }
}
