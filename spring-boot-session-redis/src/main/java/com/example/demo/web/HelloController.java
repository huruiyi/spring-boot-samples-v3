package com.example.demo.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
public class HelloController {

    private RestTemplate restTemplate;

    @Autowired
    public HelloController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }
    @GetMapping("/")
    public String hello(HttpSession session) {
        return "Hello World:" + session.getId();
    }

    @GetMapping("/test")
    public String login(HttpSession session) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.set("username", "user");
        form.set("password", "password");
        ResponseEntity<String> entity = this.restTemplate.exchange("http://localhost:8080/login", HttpMethod.POST,
                new HttpEntity<>(form, headers), String.class);
        return entity.getHeaders().getFirst("Set-Cookie");
    }
}
