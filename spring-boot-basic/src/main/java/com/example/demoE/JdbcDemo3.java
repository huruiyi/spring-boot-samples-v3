package com.example.demoE;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ContextConfiguration("classpath:applicationContextE.xml")
public class JdbcDemo3 {

  @Resource(name = "jdbcTemplate_dbcp")
  private JdbcTemplate jdbcTemplate;

  @Test
  public void demo() {
    int result = 0;
    try {
      result = jdbcTemplate.update("insert into account values(null,?,?)", "如花", 10000d);

    } catch (Exception e) {
      System.err.println(e);
    }
    if (result > 0) {
      System.out.println("添加成功!");
    } else {
      System.out.println("添加失败!");
    }
  }
}
