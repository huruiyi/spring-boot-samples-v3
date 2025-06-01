package com.example.advice;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(Exception.class)
  public Object handleException(Exception e) {
    String msg = e.getMessage();
    if (msg == null || msg.isEmpty()) {
      msg = "服务器出错";
    }
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("code", 1000);
    jsonObject.put("message", msg);
    return jsonObject;
  }

}
