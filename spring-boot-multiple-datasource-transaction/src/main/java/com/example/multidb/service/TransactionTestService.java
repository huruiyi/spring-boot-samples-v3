package com.example.multidb.service;

import com.example.multidb.dto.TransactionTestRequest;
import com.example.multidb.entity.Order;
import com.example.multidb.entity.User;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 事务测试服务 用于演示多数据源场景下的事务提交与回滚
 */
@Service
public class TransactionTestService {

  private static final Logger logger = LoggerFactory.getLogger(TransactionTestService.class);

  @Autowired
  private UserService userService;

  @Autowired
  private OrderService orderService;

  /**
   * 创建用户和订单（测试Seata分布式事务） 使用 @GlobalTransactional 注解实现跨数据源的全局事务
   *
   * @param request 请求参数
   * @return 结果消息
   */
  @GlobalTransactional(name = "create-user-and-order", rollbackFor = Exception.class)
  public String createUserAndOrder(TransactionTestRequest request) {
    logger.info("【Seata全局事务】开始创建用户和订单，XID: {}, rollback: {}", RootContext.getXID(), request.getRollback());

    try {
      // 1. 创建用户（使用 user_db 数据源）
      User user = new User();
      user.setUsername(request.getUsername());
      user.setEmail(request.getEmail());
      user.setPhone(request.getPhone());
      user.setAge(28);
      user.setAddress("测试地址");

      Long userId = userService.save(user);
      logger.info("用户创建成功，用户ID: {}, 用户名: {}", userId, request.getUsername());

      // 2. 创建订单（使用 order_db 数据源）
      Order order = new Order();
      order.setOrderNo(request.getOrderNo());
      order.setUserId(userId); // 使用插入后生成的用户ID
      order.setProductName(request.getProductName());
      order.setQuantity(request.getQuantity());
      order.setTotalPrice(java.math.BigDecimal.valueOf(request.getTotalPrice()));
      order.setStatus("PENDING");

      Long orderId = orderService.save(order);
      logger.info("订单创建成功，订单ID: {}, 订单号: {}", orderId, request.getOrderNo());

      // 3. 如果需要回滚，触发数据库异常（字段长度超长）
      if (Boolean.TRUE.equals(request.getRollback())) {
        logger.warn("rollback=true，尝试插入超长字段值触发数据库异常");

        // 插入一个超长用户名的用户，触发数据库字段长度限制异常
        User longUser = new User();
        // username 字段定义为 VARCHAR(50)，插入超过50个字符会触发数据库异常
        longUser.setUsername("very_long_username_that_exceeds_maximum_length_limit_for_database_column_" + System.currentTimeMillis());
        longUser.setEmail("test_overflow@example.com");
        longUser.setPhone("13900000001");
        longUser.setAge(30);
        longUser.setAddress("测试超长字段");

        // 这会触发数据库字段长度超长异常
        userService.save(longUser);
      }

      logger.info("事务提交成功");
      return "用户和订单创建成功！用户ID: " + userId + ", 订单号: " + request.getOrderNo() + ", 订单ID: " + orderId;

    } catch (Exception e) {
      logger.error("【Seata全局事务】处理失败，异常类型: {}, 异常信息: {}", e.getClass().getSimpleName(), e.getMessage());
      logger.error("【Seata全局事务】将触发全局回滚，XID: {}", RootContext.getXID());
      throw e;
    } finally {
      logger.info("【Seata全局事务】方法执行完成");
    }
  }

  /**
   * 创建用户和订单（使用写死的数据测试） rollback=true 时，会尝试插入超长字段值触发数据库异常
   */
  @GlobalTransactional(name = "create-user-and-order-hardcoded", rollbackFor = Exception.class)
  public String createWithHardcodedData(Boolean rollback) {
    logger.info("【Seata全局事务】使用写死的数据创建用户和订单，XID: {}, rollback: {}", RootContext.getXID(), rollback);

    TransactionTestRequest request = new TransactionTestRequest();
    request.setRollback(rollback);

    // 使用时间戳保证每次的用户名不同，避免唯一约束冲突
    request.setUsername("test_user_" + System.currentTimeMillis());
    request.setEmail("test@example.com");
    request.setPhone("13900000000");

    // 订单号使用固定格式，rollback=true 时会尝试插入相同的订单号
    if (Boolean.TRUE.equals(rollback)) {
      // 使用固定的订单号，确保第二次插入时触发唯一约束冲突
      request.setOrderNo("ROLLBACK_TEST_ORDER_" + System.currentTimeMillis());
    } else {
      request.setOrderNo("TEST_" + System.currentTimeMillis());
    }

    request.setProductName("测试商品");
    request.setQuantity(1);
    request.setTotalPrice(99.99);

    return createUserAndOrder(request);
  }
}
