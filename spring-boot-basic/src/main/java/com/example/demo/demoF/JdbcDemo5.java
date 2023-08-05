package com.example.demo.demoF;

import com.example.demo.domain.Account;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import jakarta.annotation.Resource;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration("classpath:applicationContextF.xml")
public class JdbcDemo5 {

    @Resource(name = "jdbcTemplate_c3p0")
    private JdbcTemplate jdbcTemplate;

    @Test
    public void demo0_basic() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/springjdbc");
        dataSource.setUser("root");
        dataSource.setPassword("root");

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
    }

    @Test
    public void demo1_insert() throws PropertyVetoException {

        int result = 0;
        try {
            result = jdbcTemplate.update("insert into account values(null,?,?)", "小宝abcxy", 10000d);

        } catch (Exception e) {
            System.err.println(e);
        }
        if (result > 0) {
            System.out.println("添加成功!");
        } else {
            System.out.println("添加失败!");
        }
    }

    @Test
    public void demo2_update() {
        int result = 0;
        try {
            result = jdbcTemplate.update("update account set name = ? , money = ? where id = ?", "小宝宝", 10000d, 11);
        } catch (Exception e) {
            System.err.println(e);
        }
        if (result > 0) {
            System.out.println("更新成功!");
        } else {
            System.out.println("更新失败!");
        }
    }

    @Test
    public void demo3_delete() {
        int result = 0;
        try {
            result = jdbcTemplate.update("delete from account where id = ?", 0);
        } catch (Exception e) {
            System.err.println(e);
        }
        if (result > 0) {
            System.out.println("删除成功!");
        } else {
            System.out.println("删除失败!");
        }
    }

    @Test
    public void demo4_find() {
        String name = "";
        try {
            name = jdbcTemplate.queryForObject("select name from account where id = ?", String.class, 1);
        } catch (Exception e) {
            System.err.println(e);
        }
        if (!name.isEmpty()) {
            System.out.println("成功,name=" + name);
        } else {
            System.out.println("查询失败!");
        }
    }

    @Test
    public void demo5_find_count() {
        Long count = 0l;
        try {
            count = jdbcTemplate.queryForObject("select count(1) from account ", Long.class);
        } catch (Exception e) {
            System.err.println(e);
        }
        if (count > 0) {
            System.out.println("总条数, count=" + count);
        } else {
            System.out.println("查询失败!");
        }
    }

    class MyRowMapper implements RowMapper<Account> {

        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account account = new Account();
            account.setId(rs.getLong("id"));
            account.setName(rs.getString("name"));
            account.setMoney(rs.getDouble("money"));
            return account;
        }

    }

    /**
     * 查询单条记录
     */
    @Test
    public void demo6_find_object() {
        Account account = null;
        try {
            account = jdbcTemplate.queryForObject("select * from account where id = ?", new MyRowMapper(), 3);
        } catch (Exception e) {
            System.err.println(e);
        }
        if (account != null) {
            System.out.println(account);
        } else {
            System.out.println("查询失败!");
        }
    }

    @Test
    public void demo6_find_objects() {
        List<Account> accounts = null;
        try {
            accounts = jdbcTemplate.query("select * from account", new MyRowMapper());
        } catch (Exception e) {
            System.err.println(e);
        }
        if (accounts != null && accounts.size() > 0) {
            for (Iterator<Account> iterator = accounts.iterator(); iterator.hasNext(); ) {
                Account account = (Account) iterator.next();
                System.out.println("for" + account);
            }

            for (Account account : accounts) {
                System.out.println("foreach" + account);
            }
        } else {
            System.out.println("查询失败!");
        }
    }
}
