package com.example.multidb.repository;

import com.example.multidb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户数据访问层
 */
@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setAge(rs.getInt("age"));
            user.setAddress(rs.getString("address"));
            user.setCreateTime(rs.getTimestamp("create_time") != null ? 
                rs.getTimestamp("create_time").toLocalDateTime() : null);
            user.setUpdateTime(rs.getTimestamp("update_time") != null ? 
                rs.getTimestamp("update_time").toLocalDateTime() : null);
            return user;
        }
    };

    /**
     * 根据ID查询用户
     */
    public User findById(Long id) {
        String sql = "SELECT * FROM t_user WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, id);
    }

    /**
     * 查询所有用户
     */
    public List<User> findAll() {
        String sql = "SELECT * FROM t_user";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    /**
     * 保存用户，返回插入后生成的自增ID
     */
    public Long save(User user) {
        String sql = "INSERT INTO t_user (username, email, phone, age, address, create_time, update_time) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setInt(4, user.getAge());
            ps.setString(5, user.getAddress());
            ps.setObject(6, LocalDateTime.now());
            ps.setObject(7, LocalDateTime.now());
            return ps;
        }, keyHolder);
        
        return keyHolder.getKey().longValue();
    }

    /**
     * 更新用户
     */
    public int update(User user) {
        String sql = "UPDATE t_user SET username = ?, email = ?, phone = ?, age = ?, address = ?, update_time = ? " +
                     "WHERE id = ?";
        return jdbcTemplate.update(sql,
            user.getUsername(),
            user.getEmail(),
            user.getPhone(),
            user.getAge(),
            user.getAddress(),
            LocalDateTime.now(),
            user.getId()
        );
    }

    /**
     * 根据ID删除用户
     */
    public int deleteById(Long id) {
        String sql = "DELETE FROM t_user WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    /**
     * 根据用户名查询用户
     */
    public User findByUsername(String username) {
        String sql = "SELECT * FROM t_user WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, username);
    }
}
