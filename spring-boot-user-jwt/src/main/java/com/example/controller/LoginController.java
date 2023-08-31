package com.example.controller;


import com.alibaba.fastjson2.JSONObject;
import com.example.annotation.LoginToken;
import com.example.model.User;
import com.example.service.TokenService;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  private final UserService userService;
  private final TokenService tokenService;

  public LoginController(UserService userService, TokenService tokenService) {
    this.userService = userService;
    this.tokenService = tokenService;
  }

  /**
   * localhost:8080/login?username=admin&password=admin
   */
  @PostMapping("login")
  public Object login(String username, String password) {
    JSONObject jsonObject = new JSONObject();
    User user = userService.getUser(username, password);
    if (user == null) {
      jsonObject.put("message", "登录失败！");
    } else {
      String token = tokenService.getToken(user);
      jsonObject.put("token", token);
      jsonObject.put("user", user);
    }
    return jsonObject;
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
