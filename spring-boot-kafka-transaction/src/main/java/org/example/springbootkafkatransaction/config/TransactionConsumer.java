package org.example.springbootkafkatransaction.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@Slf4j
public class TransactionConsumer {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  // 消费 input-topic，处理后发送到 output-topic
  @KafkaListener(topics = "input-topic", groupId = "tx-consumer-group")
  @Transactional("kafkaTransactionManager") // 绑定Kafka事务
  public void consumeAndForward(String message, Acknowledgment ack) {
    log.info("✅ 收到消息: {}", message);

//    ContainerProperties.AckMode.RECORD
//    ContainerProperties.AckMode.BATCH
    // 模拟业务处理
    if (message.contains("error")) {
      log.error("❌ 模拟业务异常！消息内容含 'error'，触发事务回滚");
      throw new RuntimeException("业务处理失败，事务将回滚");
    }

    // 发送新消息（加入当前事务）
    String outputMsg = "PROCESSED: " + message + " @ " + LocalDateTime.now();
    kafkaTemplate.send("output-topic", outputMsg);
    log.info("📤 已发送至 output-topic: {}", outputMsg);

    // 注意：无需手动 ack！事务提交时自动提交偏移量
  }
}