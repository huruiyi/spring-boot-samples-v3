package vip.fairy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    // 模拟幂等性存储（生产环境用 Redis 或 DB 唯一索引）
    private final ConcurrentHashMap<String, Boolean> processedMessages = new ConcurrentHashMap<>();

    /**
     * 创建订单（模拟耗时操作）
     */
    public void createOrder(String orderId, String messageId) throws Exception {
        // ✅ 幂等性检查
        if (processedMessages.containsKey(messageId)) {
            log.warn("消息已处理，跳过重复消费. messageId={}", messageId);
            return;
        }

        log.info("⏳ 开始处理订单: {}, messageId={}", orderId, messageId);

        // 模拟业务处理（如 DB 写入、调用支付等）
        Thread.sleep(2000); // 模拟耗时

        // 模拟随机异常（测试失败重试）
        if (Math.random() < 0.3) {
            throw new RuntimeException("模拟业务异常");
        }

        // ✅ 标记为已处理（幂等）
        processedMessages.put(messageId, true);
        log.info("✅ 订单创建成功: {}", orderId);
    }
}
