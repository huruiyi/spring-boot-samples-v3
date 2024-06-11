package com.example.config;


import com.example.filter.JwtAuthenticationTokenFilter;
import com.example.handler.BussinessAccessDeniedHandler;
import com.example.handler.LoginAuthenticationEntryPoint;
import com.example.handler.LoginFailureHandler;
import com.example.handler.LoginSuccessHandler;
import com.example.handler.MyLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author Fox
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  private final LoginSuccessHandler loginSuccessHandler;
  private final LoginFailureHandler loginFailureHandler;
  private final LoginAuthenticationEntryPoint loginAuthenticationEntryPoint;
  private final BussinessAccessDeniedHandler bussinessAccessDeniedHandler;
  private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
  private final MyLogoutSuccessHandler myLogoutSuccessHandler;

  public WebSecurityConfig(
      LoginSuccessHandler loginSuccessHandler,
      LoginFailureHandler loginFailureHandler,
      LoginAuthenticationEntryPoint loginAuthenticationEntryPoint,
      BussinessAccessDeniedHandler bussinessAccessDeniedHandler,
      JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
      MyLogoutSuccessHandler myLogoutSuccessHandler) {
    this.loginSuccessHandler = loginSuccessHandler;
    this.loginFailureHandler = loginFailureHandler;
    this.loginAuthenticationEntryPoint = loginAuthenticationEntryPoint;
    this.bussinessAccessDeniedHandler = bussinessAccessDeniedHandler;
    this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    this.myLogoutSuccessHandler = myLogoutSuccessHandler;
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    //对请求进行访问控制设置
    http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
        //设置哪些路径可以直接访问，不需要认证
        .requestMatchers("/user/**").hasRole("admin")
        //.requestMatchers("/index").permitAll()
        .anyRequest().authenticated() //其他路径的请求都需要认证
    );

    //自定义登录逻辑
    http.formLogin(formLogin -> formLogin
        .loginProcessingUrl("/login")//登录访问路径：前台界面提交表单之后跳转到这个路径进行UserDetailsService的验证，必须和表单提交接口一样
        .successHandler(loginSuccessHandler)
        .failureHandler(loginFailureHandler)
    );

    //添加JWT登录拦截器，在登录之前获取token并校验
    http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    //访问受限后的异常处理
    http.exceptionHandling(exceptionHandling -> exceptionHandling
        .authenticationEntryPoint(loginAuthenticationEntryPoint)
        .accessDeniedHandler(bussinessAccessDeniedHandler)
    );
    //自定义退出登录逻辑
    http.logout(logout -> logout
        .logoutSuccessHandler(myLogoutSuccessHandler)
    );
    //关闭跨站点请求伪造csrf防护
    http.csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }


}
