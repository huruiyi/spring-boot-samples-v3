package vip.fairy.springbootrabbitmqdemo.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {


  public static final String ORDER_EXCHANGE = "order.exchange";
  public static final String ORDER_ROUTING_KEY = "order.create";


  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

    // 设置 ConfirmCallback：监听消息是否到达 Broker
    rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
      @Override
      public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
          System.out.println("✅ 消息已确认送达 Broker, ID: " + (correlationData != null ? correlationData.getId() : "N/A"));
        } else {
          System.out.println("❌ 消息发送失败, ID: " + (correlationData != null ? correlationData.getId() : "N/A") + ", 原因: " + cause);
        }
      }
    });

    // 设置 ReturnCallback：监听消息是否被路由到队列（如 routingKey 无效）
    rabbitTemplate.setReturnsCallback(returned -> {
      System.out.println("⚠️ 消息未路由到任何队列: exchange=" + returned.getExchange() +
          ", routingKeys=" + returned.getRoutingKey() +
          ", replyCode=" + returned.getReplyCode() +
          ", replyText=" + returned.getReplyText());
    });

    return rabbitTemplate;
  }
}
