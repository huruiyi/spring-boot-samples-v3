package com.example.demo8;

/**
 * 纯Bean
 */
public class ProductService {

  private ProductDao productDao;
  private OrderDao orderDao;

  public void setProductDao(ProductDao productDao) {
    this.productDao = productDao;
  }

  public void setOrderDao(OrderDao orderDao) {
    this.orderDao = orderDao;
  }

  public void save() {
    System.out.println("ProductService save()");
    productDao.save();
    orderDao.save();
  }
}
