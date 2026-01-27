package com.example.multidb.controller;

import com.example.multidb.entity.Order;
import com.example.multidb.entity.User;
import com.example.multidb.service.OrderService;
import com.example.multidb.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private UserService userService;

  /**
   * 根据ID查询订单
   */
  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
    Order order = orderService.findById(id);
    Map<String, Object> response = new HashMap<>();
    response.put("code", 200);
    response.put("message", "查询成功");
    response.put("data", order);
    return ResponseEntity.ok(response);
  }

  /**
   * 根据订单号查询订单
   */
  @GetMapping("/orderNo/{orderNo}")
  public ResponseEntity<Map<String, Object>> findByOrderNo(@PathVariable String orderNo) {
    Order order = orderService.findByOrderNo(orderNo);
    Map<String, Object> response = new HashMap<>();
    response.put("code", 200);
    response.put("message", "查询成功");
    response.put("data", order);
    return ResponseEntity.ok(response);
  }

  /**
   * 根据用户ID查询订单列表
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<Map<String, Object>> findByUserId(@PathVariable Long userId) {
    List<Order> orders = orderService.findByUserId(userId);
    Map<String, Object> response = new HashMap<>();
    response.put("code", 200);
    response.put("message", "查询成功");
    response.put("data", orders);
    return ResponseEntity.ok(response);
  }

  /**
   * 根据用户名查询订单列表
   */
  @GetMapping("/username/{username}")
  public ResponseEntity<Map<String, Object>> findByUsername(@PathVariable String username) {
    User user = userService.findByUsername(username);
    Map<String, Object> response = new HashMap<>();
    if (user == null) {
      response.put("code", 404);
      response.put("message", "用户不存在");
      response.put("data", null);
      return ResponseEntity.ok(response);
    }
    List<Order> orders = orderService.findByUserId(user.getId());
    response.put("code", 200);
    response.put("message", "查询成功");
    response.put("data", orders);
    return ResponseEntity.ok(response);
  }

  /**
   * 查询所有订单
   */
  @GetMapping
  public ResponseEntity<Map<String, Object>> findAll() {
    List<Order> orders = orderService.findAll();
    Map<String, Object> response = new HashMap<>();
    response.put("code", 200);
    response.put("message", "查询成功");
    response.put("data", orders);
    return ResponseEntity.ok(response);
  }

  /**
   * 保存订单，返回插入后的自增ID
   */
  @PostMapping
  public ResponseEntity<Map<String, Object>> save(@RequestBody Order order) {
    Long orderId = orderService.save(order);
    Map<String, Object> response = new HashMap<>();
    response.put("code", 200);
    response.put("message", "保存成功");
    response.put("data", orderId);
    response.put("orderId", orderId);
    return ResponseEntity.ok(response);
  }

  /**
   * 更新订单
   */
  @PutMapping
  public ResponseEntity<Map<String, Object>> update(@RequestBody Order order) {
    int result = orderService.update(order);
    Map<String, Object> response = new HashMap<>();
    response.put("code", 200);
    response.put("message", "更新成功");
    response.put("data", result);
    return ResponseEntity.ok(response);
  }

  /**
   * 更新订单状态
   */
  @PutMapping("/{id}/status")
  public ResponseEntity<Map<String, Object>> updateStatus(
      @PathVariable Long id,
      @RequestParam String status) {
    int result = orderService.updateStatus(id, status);
    Map<String, Object> response = new HashMap<>();
    response.put("code", 200);
    response.put("message", "更新成功");
    response.put("data", result);
    return ResponseEntity.ok(response);
  }

  /**
   * 根据ID删除订单
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> deleteById(@PathVariable Long id) {
    int result = orderService.deleteById(id);
    Map<String, Object> response = new HashMap<>();
    response.put("code", 200);
    response.put("message", "删除成功");
    response.put("data", result);
    return ResponseEntity.ok(response);
  }
}
