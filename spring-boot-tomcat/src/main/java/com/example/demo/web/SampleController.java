package com.example.demo.web;

import com.example.demo.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    private HelloWorldService helloWorldService;

    @GetMapping("/")
    public String helloWorld() {
        return this.helloWorldService.getHelloMessage();
    }

    @GetMapping(value = "/world", produces = "application/json;charset=UTF-8")
    public String word() {
        return "世界你好！";
    }

}
