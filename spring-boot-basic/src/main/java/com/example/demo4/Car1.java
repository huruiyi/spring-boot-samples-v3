package com.example.demo4;

public class Car1 {

  private String name;
  private Double price;

  public Car1(String name, Double price) {
    super();
    this.name = name;
    this.price = price;
  }

  @Override
  public String toString() {
    return "Car [name=" + name + ", price=" + price + "]";
  }

}
