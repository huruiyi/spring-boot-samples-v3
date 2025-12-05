package vip.fairy.springbootrabbitmqdemo.config;


import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import vip.fairy.springbootrabbitmqdemo.service.OrderService;

import java.io.IOException;

@Component
public class OrderMessageListener {

  private static final Logger log = LoggerFactory.getLogger(OrderMessageListener.class);

  @Autowired
  private OrderService orderService;

  @Autowired
  private ObjectMapper objectMapper; // 用于解析 JSON

  @RabbitListener(queues = RabbitConfig.ORDER_QUEUE)
  public void onMessage(Message message, Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {

//    String messageId = message
//        .getMessageProperties()
//        .getHeaders()
//        .get("spring_returned_message_correlation")
//        .toString();
    String messageId = message.getMessageProperties().getMessageId(); // 建议生产者设置
    byte[] body = message.getBody();

    try {
      // 解析消息
      JsonNode json = objectMapper.readTree(body);
      String orderId = json.get("orderId").asText();

      // 执行业务
      orderService.createOrder(orderId, messageId);

      // ✅ 业务成功 → 手动 ACK
      channel.basicAck(deliveryTag, false);
      log.info("📨 消息已确认: deliveryTag={}", deliveryTag);

    } catch (Exception e) {
      log.error("💥 处理消息失败, deliveryTag={}, messageId={}", deliveryTag, messageId, e);

      try {
        // ❌ 业务失败 → 拒绝消息
        // requeue=false 表示不重回原队列（会进死信队列）
        // 如果希望立即重试，可设 requeue=true（但可能无限循环）
        channel.basicNack(deliveryTag, false, false);
        log.warn("📤 消息已拒绝并路由至死信队列: deliveryTag={}", deliveryTag);
      } catch (IOException ioException) {
        log.error("❌ 发送 NACK 失败", ioException);
      }
    }
  }
}
