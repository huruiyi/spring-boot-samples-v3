package vip.fairy.model;

import lombok.Data;

@Data
public class Student {

  public String name;
  public int age;

  public Student(String name, int age) {
    this.name = name;
    this.age = age;
  }

}
