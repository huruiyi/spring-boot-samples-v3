package com.mycompany.generic.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponseDTO {

  private String token;
  private String type = "Bearer";
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private List<String> roles;

  public JwtResponseDTO(String accessToken, Long id, String firstName, String lastName, String email, List<String> roles) {
    this.token = accessToken;
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.roles = roles;
  }

}
