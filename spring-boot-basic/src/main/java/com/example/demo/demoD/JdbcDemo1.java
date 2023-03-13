package com.example.demo.demoD;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JdbcDemo1 {

    @Test
    public void demo() throws DataAccessException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        /* dataSource.setUrl("jdbc:mysql:///springjdbc"); */
        dataSource.setUrl("jdbc:mysql://localhost:3306/springjdbc");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        // 创建Jdbc模板
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        int result = 0;
        try {
            result = jdbcTemplate.update("insert into account values(null,?,?)", "小红", 10000d);

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
