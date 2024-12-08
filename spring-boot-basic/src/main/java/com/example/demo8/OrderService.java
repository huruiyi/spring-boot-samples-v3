package com.example.demo8;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Bean和注解混合使用
 */
public class OrderService {

  @Autowired
  //@Resource(name = "productDao")
  private ProductDao productDao;

  @Autowired
  //@Resource(name = "orderDao")
  private OrderDao orderDao;

  public void save() {
    System.out.println("OrderService save()");
    productDao.save();
    orderDao.save();
  }
}
