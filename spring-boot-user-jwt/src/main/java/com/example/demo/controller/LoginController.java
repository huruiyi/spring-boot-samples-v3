package com.example.demo.controller;


import com.alibaba.fastjson2.JSONObject;
import com.example.demo.annotation.LoginToken;
import com.example.demo.model.User;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  @Autowired
  private UserService userService;
  @Autowired
  private TokenService tokenService;

  /**
   * localhost:8080/login?username=admin&password=admin
   */
  @PostMapping("login")
  public Object login(String username, String password) {
    JSONObject jsonObject = new JSONObject();
    User user = userService.getUser(username, password);
    if (user == null) {
      jsonObject.put("message", "登录失败！");
      return jsonObject;
    } else {
      String token = tokenService.getToken(user);
      jsonObject.put("token", token);
      jsonObject.put("user", user);
      return jsonObject;
    }
  }

  /**
   * localhost:8080/getMessage header：token=.....
   */
  @LoginToken
  @PostMapping("/message")
  public String getMessage() {
    return "你已通过验证";
  }

}
