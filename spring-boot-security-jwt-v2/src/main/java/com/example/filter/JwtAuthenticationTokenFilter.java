package com.example.filter;


import com.example.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    //1.从请求头中取出token，进行判断，如果没有携带token，则继续往下走其他的其他的filter逻辑
    String tokenValue = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (!StringUtils.hasText(tokenValue)) {
      filterChain.doFilter(request, response);
      return;
    }
    //2. 校验token
    //2.1 将token切割前缀“bearer ”，然后使用封装的JWT工具解析token，得到一个map对象
    String token = tokenValue.substring("bearer ".length());
    Map<String, Object> map = JWTUtils.parseToken(token);
    //2.2 取出token中的过期时间，调用JWT工具中封装的过期时间校验，如果token已经过期，则删除登录的用户，继续往下走其他filter逻辑
    if (JWTUtils.isExpiresIn((long) map.get("expiresIn"))) {
      //token 已经过期
      SecurityContextHolder.getContext().setAuthentication(null);
      filterChain.doFilter(request, response);
      return;
    }

    String username = (String) map.get("username");
    if (StringUtils.hasText(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
      //获取用户信息
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (userDetails != null && userDetails.isEnabled()) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //设置用户登录状态
        log.info("authenticated user {}, setting security context", username);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);

  }
}

