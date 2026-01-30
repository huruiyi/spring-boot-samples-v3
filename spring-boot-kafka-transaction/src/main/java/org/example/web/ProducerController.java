package org.example.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
@Slf4j
public class ProducerController {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  // 发送正常消息（应成功）
  @GetMapping("/send/success")
  @Transactional  // 添加事务注解
  public String sendSuccess(@RequestParam String msg) {
    kafkaTemplate.send("input-topic", "SUCCESS: " + msg);
    log.info("📨 已发送成功消息: {}", msg);
    return "消息已发送至 input-topic（预期：output-topic 收到处理结果）";
  }

  // 发送异常消息（应回滚）
  @GetMapping("/send/error")
  @Transactional  // 添加事务注解
  public String sendError(@RequestParam String msg) {
    kafkaTemplate.send("input-topic", "error-trigger: " + msg); // 含 "error" 触发异常
    log.info("📨 已发送异常消息: {}", msg);
    return "消息已发送至 input-topic（预期：output-topic 无记录，input-topic 偏移量未提交）";
  }

  // 查询 output-topic 消息（简化版，实际需用消费者）
  @GetMapping("/output/messages")
  public String checkOutput() {
    return "请用 Kafka 工具查看 output-topic 内容（如 kafka-console-consumer）";
  }
}
