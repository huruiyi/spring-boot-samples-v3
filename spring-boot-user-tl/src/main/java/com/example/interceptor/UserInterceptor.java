package com.example.interceptor;

import com.example.model.UserInfo;
import com.example.utils.UserThreadLocal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
    // 此处实际应该根据header的token解析出用户
    // 本处为了简单，直接虚构一个用户
    UserThreadLocal.set(UserInfo.builder().userId(3L).userName("Tony").build());
    return true;
  }

  @Override
  public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception e) {
    UserThreadLocal.clear();
  }
}
