package com.example.demoC;

public class OrderDao {

  public void save() {
    System.out.println("OrderDao save()");
  }

  public String delete() {
    System.out.println("OrderDao delete()");
    return "xxxxx被删除了";
  }

  public void update() {
    System.out.println("OrderDao update()");
  }

  public void find() {
    System.out.println("OrderDao find()");
    // int a = 1 / 0;
  }
}
