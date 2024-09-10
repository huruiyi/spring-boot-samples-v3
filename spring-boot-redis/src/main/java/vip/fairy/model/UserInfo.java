package vip.fairy.model;


import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserInfo {
  public Long userId;
  public String userName;
  public String userEmail;
  public LocalDateTime userBirthday;
}
