package vip.fairy.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import vip.fairy.config.RabbitConfig;

import java.util.UUID;

@Service
public class MessageProducerService {

  private final RabbitTemplate rabbitTemplate;

  public MessageProducerService(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void snedCorrelationData(String message) {
    String messageId = UUID.randomUUID().toString().replace("-", "");
    CorrelationData correlationData = new CorrelationData(messageId);
    rabbitTemplate.convertAndSend(RabbitConfig.ORDER_EXCHANGE, RabbitConfig.ORDER_ROUTING_KEY, message, correlationData);
    System.out.println("📤 发送消息：" + message + ", ID: " + messageId);
  }

  public void sendMessage(String message) {
    String messageId = UUID.randomUUID().toString().replace("-", "");

    MessageProperties props = new MessageProperties();
    props.setMessageId(messageId);
    props.setContentType("application/json");
    props.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

    Message messageObj = new Message(message.getBytes(), props);

    rabbitTemplate.send("order.exchange", "order.create", messageObj);
    System.out.println("📤 已发送消息, messageId: " + messageId);
  }

  public void sendReliableMessage(String payload) {
    String messageId = UUID.randomUUID().toString().replace("-", "");

    // 1. 设置消息属性（供消费者使用）
    MessageProperties props = new MessageProperties();
    props.setMessageId(messageId);          // ← 消费者能读到
    props.setContentType("application/json");
    props.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

    Message message = new Message(payload.getBytes(), props);

    // 2. 创建 CorrelationData（供生产者 Confirm 使用）
    CorrelationData correlationData = new CorrelationData(messageId); // 可用同一个 ID

    // 3. 发送（注意：send 方法也支持传 CorrelationData）
    rabbitTemplate.send(
        RabbitConfig.ORDER_EXCHANGE,
        RabbitConfig.ORDER_ROUTING_KEY,
        message,
        correlationData  // ← 关键：关联 Confirm 回调
    );
    System.out.println("📤 已发送可靠消息, messageId: " + messageId + ", payload" + payload);

  }

}
