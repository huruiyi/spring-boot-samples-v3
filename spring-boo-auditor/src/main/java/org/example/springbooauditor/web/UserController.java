package org.example.springbooauditor.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {

  @RequestMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/logout")
  public String performLogout() {
    return "redirect:/user/login";
  }

}
