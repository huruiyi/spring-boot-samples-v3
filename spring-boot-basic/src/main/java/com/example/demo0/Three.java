package com.example.demo0;

import org.springframework.stereotype.Component;

@Component
public class Three {

  public Three() {
    System.out.println("three");
  }

  public Three(String three) {
    System.out.println(three);
  }
}
