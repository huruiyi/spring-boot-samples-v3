package com.example.demo1;

/**
 * 用户管理DAO层实现类
 */
public class UserDAOImpl implements UserDAO {

  private String name;

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void save() {
    System.out.println("UserDAOImpl执行了..." + name);
  }

}
