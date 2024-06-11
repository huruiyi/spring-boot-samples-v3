package com.example.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fox
 */
@RestController
@RequestMapping("/user")
public class UserController {

  @RequestMapping("/getCurrentUser")
  public Object getCurrentUser(Authentication authentication, Principal principal) {
    Map<String, Object> result = new HashMap<>();
    result.put("authentication", authentication);
    result.put("principal", principal);
    return result;
  }

}
