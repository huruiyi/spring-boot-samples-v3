package vip.fairy.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequestDTO {
  private String username;
  private String email;
  private String password;
}
