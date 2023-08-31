package com.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    @RequestMapping({"/", "/thymeleaf"})
    public String index() {
        return "thymeleaf/index";
    }


}
