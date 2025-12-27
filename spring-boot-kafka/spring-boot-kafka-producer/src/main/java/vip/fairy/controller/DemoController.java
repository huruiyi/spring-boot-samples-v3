package vip.fairy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.fairy.producer.KafkaProducer;

@RestController
public class DemoController {

  private final KafkaProducer kafkaProducer;

  public DemoController(KafkaProducer kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }

  @GetMapping("/publish")
  public String publishMessage(@RequestParam("msg") String msg) {
    return kafkaProducer.sendMessage(msg);
  }

  @PostMapping("/publish")
  public String publishObject(@RequestBody String obj) {
    return kafkaProducer.sendMessage(obj);
  }
}
