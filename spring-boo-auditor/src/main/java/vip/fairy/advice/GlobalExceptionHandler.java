package vip.fairy.advice;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vip.fairy.exception.BusinessException;
import vip.fairy.exception.ErrorResponse;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  // 自定义业务异常
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
    return ResponseEntity.status(ex.getStatus())
        .body(new ErrorResponse(
            ex.getStatus(),
            ex.getCode(),
            ex.getMessage()
        ));
  }

  // 认证失败异常
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "AUTH_FAILED",
            "认证失败：用户名或密码错误"
        ));
  }

  // 权限不足异常
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(new ErrorResponse(
            HttpStatus.FORBIDDEN.value(),
            "ACCESS_DENIED",
            "权限不足，拒绝访问"
        ));
  }

  // 请求参数类型不匹配
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String error = ex.getName() + "参数类型不匹配，期望类型: " + ex.getRequiredType().getName();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "INVALID_PARAM",
            error
        ));
  }


// 处理表单数据绑定时验证失败异常,已过时，用MethodArgumentNotValidException，handleMethodArgumentNotValid替换
//  @Override
//  protected ResponseEntity<Object> handleBindException(
//      BindException ex,
//      HttpHeaders headers,
//      HttpStatusCode status,
//      WebRequest request) {
//
//    Map<String, String> errors = new HashMap<>();
//    ex.getBindingResult().getFieldErrors()
//        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
//
//    ErrorResponse response = new ErrorResponse(
//        HttpStatus.BAD_REQUEST.value(),
//        "VALIDATION_FAILED",
//        "表单验证失败"
//    ).addData("errors", errors);
//
//    return ResponseEntity.badRequest().body(response);
//  }

  // 处理 @Valid 注解验证失败异常
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
      WebRequest request) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    ErrorResponse response = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "VALIDATION_FAILED",
        "参数验证失败"
    ).addData("errors", errors);

    return ResponseEntity.badRequest().body(response);
  }

  // 处理缺少请求参数异常
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    ErrorResponse response = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "MISSING_PARAM",
        "缺少必要参数: " + ex.getParameterName()
    );

    return ResponseEntity.badRequest().body(response);
  }

  // 处理请求方法不支持异常
  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    ErrorResponse response = new ErrorResponse(
        HttpStatus.METHOD_NOT_ALLOWED.value(),
        "METHOD_NOT_ALLOWED",
        "请求方法不支持: " + ex.getMethod()
    ).addData("supportedMethods", ex.getSupportedHttpMethods());

    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
  }

  // 处理媒体类型不支持异常
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    ErrorResponse response = new ErrorResponse(
        HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
        "UNSUPPORTED_MEDIA_TYPE",
        "不支持的媒体类型: " + ex.getContentType()
    ).addData("supportedMediaTypes", ex.getSupportedMediaTypes());

    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
  }

  // 处理消息不可读异常（如JSON解析失败）
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    ErrorResponse response = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "INVALID_REQUEST",
        "请求体格式错误: " + ex.getMessage()
    );

    return ResponseEntity.badRequest().body(response);
  }

  // 处理404 Not Found异常
  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    ErrorResponse response = new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        "RESOURCE_NOT_FOUND",
        "请求的资源不存在: " + ex.getRequestURL()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  // 处理其他未捕获的异常
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
    // 记录详细错误日志
    logger.error("系统内部错误", ex);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "INTERNAL_ERROR",
            "系统内部错误，请稍后重试"
        ));
  }
}
