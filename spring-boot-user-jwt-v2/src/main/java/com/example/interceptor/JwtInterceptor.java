package com.example.interceptor;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.advice.BusinessException;
import com.example.annotation.LoginToken;
import com.example.annotation.PassToken;
import com.example.entities.TSBaseUser;
import com.example.service.BaseUserInfo;
import com.example.service.IBaseUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class JwtInterceptor implements HandlerInterceptor {

  @Autowired
  private IBaseUserService userService;

  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
    String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
    // 如果不是映射到方法直接通过
    if (!(object instanceof HandlerMethod handlerMethod)) {
      return true;
    }
    Method method = handlerMethod.getMethod();
    //检查是否有passtoken注释，有则跳过认证
    if (method.isAnnotationPresent(PassToken.class)) {
      PassToken passToken = method.getAnnotation(PassToken.class);
      if (passToken.required()) {
        return true;
      }
    }
    // 检查有没有需要用户权限的注解
    if (method.isAnnotationPresent(LoginToken.class)) {
      LoginToken loginToken = method.getAnnotation(LoginToken.class);
      if (loginToken.required()) {
        // 执行认证
        if (token == null) {
          throw new BusinessException(401, "无token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
          userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
          throw new BusinessException(401, "未授权");
        }
        TSBaseUser user = userService.getUser(userId);
        if (user == null) {
          throw new BusinessException(500, "用户不存在，请重新登录");
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
          jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
          throw new BusinessException(401, "未授权");
        }

        // 存储登陆用户信息
        BaseUserInfo.setUser(user.getUserName(), userId);
        return true;
      }
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

  }
}
