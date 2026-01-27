package com.example.multidb.repository;

import com.example.multidb.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单数据访问层
 */
@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Order> orderRowMapper = new RowMapper<Order>() {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(rs.getLong("id"));
            order.setOrderNo(rs.getString("order_no"));
            order.setUserId(rs.getLong("user_id"));
            order.setProductName(rs.getString("product_name"));
            order.setQuantity(rs.getInt("quantity"));
            order.setTotalPrice(rs.getBigDecimal("total_price"));
            order.setStatus(rs.getString("status"));
            order.setCreateTime(rs.getTimestamp("create_time") != null ? 
                rs.getTimestamp("create_time").toLocalDateTime() : null);
            order.setUpdateTime(rs.getTimestamp("update_time") != null ? 
                rs.getTimestamp("update_time").toLocalDateTime() : null);
            return order;
        }
    };

    /**
     * 根据ID查询订单
     */
    public Order findById(Long id) {
        String sql = "SELECT * FROM t_order WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, orderRowMapper, id);
    }

    /**
     * 根据订单号查询订单
     */
    public Order findByOrderNo(String orderNo) {
        String sql = "SELECT * FROM t_order WHERE order_no = ?";
        return jdbcTemplate.queryForObject(sql, orderRowMapper, orderNo);
    }

    /**
     * 根据用户ID查询订单列表
     */
    public List<Order> findByUserId(Long userId) {
        String sql = "SELECT * FROM t_order WHERE user_id = ?";
        return jdbcTemplate.query(sql, orderRowMapper, userId);
    }

    /**
     * 查询所有订单
     */
    public List<Order> findAll() {
        String sql = "SELECT * FROM t_order";
        return jdbcTemplate.query(sql, orderRowMapper);
    }

    /**
     * 保存订单，返回插入后生成的自增ID
     */
    public Long save(Order order) {
        String sql = "INSERT INTO t_order (order_no, user_id, product_name, quantity, total_price, status, create_time, update_time) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getOrderNo());
            ps.setLong(2, order.getUserId());
            ps.setString(3, order.getProductName());
            ps.setInt(4, order.getQuantity());
            ps.setBigDecimal(5, order.getTotalPrice());
            ps.setString(6, order.getStatus());
            ps.setObject(7, LocalDateTime.now());
            ps.setObject(8, LocalDateTime.now());
            return ps;
        }, keyHolder);
        
        return keyHolder.getKey().longValue();
    }

    /**
     * 更新订单
     */
    public int update(Order order) {
        String sql = "UPDATE t_order SET order_no = ?, user_id = ?, product_name = ?, quantity = ?, " +
                     "total_price = ?, status = ?, update_time = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
            order.getOrderNo(),
            order.getUserId(),
            order.getProductName(),
            order.getQuantity(),
            order.getTotalPrice(),
            order.getStatus(),
            LocalDateTime.now(),
            order.getId()
        );
    }

    /**
     * 更新订单状态
     */
    public int updateStatus(Long id, String status) {
        String sql = "UPDATE t_order SET status = ?, update_time = ? WHERE id = ?";
        return jdbcTemplate.update(sql, status, LocalDateTime.now(), id);
    }

    /**
     * 根据ID删除订单
     */
    public int deleteById(Long id) {
        String sql = "DELETE FROM t_order WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
