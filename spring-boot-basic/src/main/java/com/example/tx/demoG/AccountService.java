package com.example.tx.demoG;

public interface AccountService {

  /**
   * @param from
   * @param to
   * @param money
   */
  public void transfer(String from, String to, Double money);
}
