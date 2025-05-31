package org.example.springbooauditor.auditor;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("customAuditorAware") // 与@EnableJpaAuditing中的auditorAwareRef对应
public class CustomAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    // 从Security上下文中获取当前用户
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.empty(); // 未认证用户可返回默认值或空
    }
    // 返回用户标识（如用户名、用户ID）
    return Optional.of(authentication.getName());
  }


  /**
   * 非 Security 场景（如自定义认证）
   */
//  @Override
//  public Optional<String> getCurrentAuditor() {
//    // 示例：从自定义ThreadLocal中获取当前用户ID
//    String currentUserId = UserContext.getCurrentUserId(); // 自定义工具类
//    return Optional.ofNullable(currentUserId);
//  }


}
