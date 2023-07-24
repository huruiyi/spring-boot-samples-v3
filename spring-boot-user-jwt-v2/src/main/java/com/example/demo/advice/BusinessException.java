package com.example.demo.advice;

public class BusinessException extends Exception {

  private final Integer code;
  private final String message;


  public BusinessException(Integer code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

  public BusinessException(Integer code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
    this.message = String.format("%s %s", message, cause.getMessage());
  }

  public Integer getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
