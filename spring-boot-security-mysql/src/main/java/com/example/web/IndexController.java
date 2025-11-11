package com.example.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class IndexController {

  @RequestMapping("/")
  public String login() {
    return "index";
  }

}
