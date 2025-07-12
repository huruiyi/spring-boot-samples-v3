package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class UserSessionService {

  @Autowired
  private PersistentTokenRepository tokenRepository;

  public void logoutUserByUsername(String username) {
    tokenRepository.removeUserTokens(username);
  }

}
