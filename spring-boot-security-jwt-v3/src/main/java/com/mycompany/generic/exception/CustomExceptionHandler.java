package com.mycompany.generic.exception;

import com.mycompany.generic.dto.ErrorDTO;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ErrorDTO>> handleFieldValidation(MethodArgumentNotValidException manv) {
    List<ErrorDTO> errorModelList = new ArrayList<>();
    ErrorDTO errorModel;
    List<FieldError> fieldErrorList = manv.getBindingResult().getFieldErrors();

    for (FieldError fe : fieldErrorList) {
      logger.debug("Inside field validation: {} - {}", fe.getField(), fe.getDefaultMessage());
      logger.info("Inside field validation: {} - {}", fe.getField(), fe.getDefaultMessage());
      errorModel = new ErrorDTO();
      errorModel.setCode(fe.getField());
      errorModel.setMessage(fe.getDefaultMessage());
      errorModelList.add(errorModel);
    }

    return new ResponseEntity<>(errorModelList, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<List<ErrorDTO>> handleBusinessException(BusinessException bex) {
    for (ErrorDTO em : bex.getErrors()) {
      logger.debug("BusinessException is thrown - level- debug: {} - {}", em.getCode(), em.getMessage());
      logger.info("BusinessException is thrown - level- info: {} - {}", em.getCode(), em.getMessage());
      logger.warn("BusinessException is thrown - level-warn: {} - {}", em.getCode(), em.getMessage());
      logger.error("BusinessException is thrown - level-error: {} - {}", em.getCode(), em.getMessage());
    }
    return new ResponseEntity<>(bex.getErrors(), HttpStatus.BAD_REQUEST);
  }

}
