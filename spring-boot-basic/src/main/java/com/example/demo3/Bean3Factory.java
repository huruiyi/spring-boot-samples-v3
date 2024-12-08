package com.example.demo3;

/**
 * Bean3的实例工厂
 */
public class Bean3Factory {

  public Bean3 createBean3() {
    System.out.println("Bean3的实例工厂执行了...");
    return new Bean3();
  }
}
