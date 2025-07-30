package com.example.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapSessionRegistry {

  // 存储用户名到 SessionID 的映射
  private static final Map<String, String> ACTIVE_SESSIONS = new ConcurrentHashMap<>();

  public static void registerSession(String username, String sessionId) {
    ACTIVE_SESSIONS.put(username, sessionId);
  }

  public static String getSessionId(String username) {
    return ACTIVE_SESSIONS.get(username);
  }

  public static void removeSession(String username) {
    ACTIVE_SESSIONS.remove(username);
  }

}
