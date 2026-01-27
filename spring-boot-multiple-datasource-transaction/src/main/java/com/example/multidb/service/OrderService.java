package com.example.multidb.service;

import com.example.multidb.annotation.DataSource;
import com.example.multidb.constant.DataSourceType;
import com.example.multidb.entity.Order;
import com.example.multidb.repository.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单服务层
 */
@Service
@DataSource(DataSourceType.ORDER)
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;

  /**
   * 根据ID查询订单
   */
  public Order findById(Long id) {
    return orderRepository.findById(id);
  }

  /**
   * 根据订单号查询订单
   */
  public Order findByOrderNo(String orderNo) {
    return orderRepository.findByOrderNo(orderNo);
  }

  /**
   * 根据用户ID查询订单列表
   */
  public List<Order> findByUserId(Long userId) {
    return orderRepository.findByUserId(userId);
  }

  /**
   * 查询所有订单
   */
  public List<Order> findAll() {
    return orderRepository.findAll();
  }

  /**
   * 保存订单，返回插入后的自增ID
   */
  public Long save(Order order) {
    return orderRepository.save(order);
  }

  /**
   * 更新订单
   */
  public int update(Order order) {
    return orderRepository.update(order);
  }

  /**
   * 更新订单状态
   */
  public int updateStatus(Long id, String status) {
    return orderRepository.updateStatus(id, status);
  }

  /**
   * 根据ID删除订单
   */
  public int deleteById(Long id) {
    return orderRepository.deleteById(id);
  }
}
