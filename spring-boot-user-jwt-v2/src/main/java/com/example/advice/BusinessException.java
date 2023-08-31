package com.example.advice;

import lombok.Getter;

public class BusinessException extends Exception {

  @Getter
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

  @Override
  public String getMessage() {
    return message;
  }

}
