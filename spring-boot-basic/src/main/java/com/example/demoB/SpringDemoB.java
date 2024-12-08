package com.example.demoB;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ContextConfiguration("classpath:applicationContextB.xml")
public class SpringDemoB {

  @Resource(name = "productDao")
  private ProductDao productDao;

  @Test
  public void demo1() {
    productDao.save();
    productDao.update();
    productDao.delete();
    productDao.find();
  }
}
