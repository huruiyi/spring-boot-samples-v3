```java
@Component
public class CustomAuthenticationSuccessHandler
    extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession(false); // 安全！直接可用

        // 自定义逻辑
        log.info("Session ID: {}", session.getId());

        // 别忘了调用父类（如果你要重定向）
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
```

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(form -> form
                .successHandler(successHandler) // ← 注册处理器
            );
        return http.build();
    }
}

```


```java
@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        HttpSession session = request.getSession(false); // ✅ 一定存在
        // 处理逻辑...
        
        response.sendRedirect("/home");
    }
}
```
