package com.example.handler;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;


@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    Object principal = authentication.getPrincipal();
    response.setContentType("application/json;charset=utf-8");
    PrintWriter out = response.getWriter();
    out.write(JSON.toJSONString(principal));
    out.flush();
    out.close();
  }
}
