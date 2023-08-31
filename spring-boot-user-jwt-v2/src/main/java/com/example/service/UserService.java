package com.example.service;


import com.example.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  public User getUser(String userid, String password) {
    if ("admin".equals(userid) && "admin".equals(password)) {
      User user = new User();
      user.setUserID("admin");
      user.setUserName("admin");
      user.setPassWord("admin");
      return user;
    } else {
      return null;
    }
  }

  public User getUser(String userid) {
    if ("admin".equals(userid)) {
      User user = new User();
      user.setUserID("admin");
      user.setUserName("admin");
      user.setPassWord("admin");
      return user;
    } else {
      return null;
    }
  }
}
