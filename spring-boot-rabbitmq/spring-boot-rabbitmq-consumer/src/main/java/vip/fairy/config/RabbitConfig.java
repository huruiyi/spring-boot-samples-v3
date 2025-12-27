package vip.fairy.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
  public static final String ORDER_EXCHANGE = "order.exchange";
  public static final String ORDER_QUEUE = "order.queue";
  public static final String ORDER_ROUTING_KEY = "order.create";

  // 死信相关（可选但推荐）
  public static final String DLX_EXCHANGE = "dlx.exchange";
  public static final String DLQ_QUEUE = "dlq.order.queue";

  @Bean
  public DirectExchange orderExchange() {
    return new DirectExchange(ORDER_EXCHANGE, true, false); // durable=true
  }

  @Bean
  public Queue orderQueue() {
    return QueueBuilder.durable(ORDER_QUEUE)
        .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
        .withArgument("x-dead-letter-routing-key", "dlq.order")
        .build();
  }

  @Bean
  public Binding orderBinding() {
    return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(ORDER_ROUTING_KEY);
  }

  // ===== 死信队列（用于处理多次失败的消息）=====
  @Bean
  public DirectExchange dlxExchange() {
    return new DirectExchange(DLX_EXCHANGE, true, false);
  }

  @Bean
  public Queue dlqQueue() {
    return QueueBuilder.durable(DLQ_QUEUE).build();
  }

  @Bean
  public Binding dlqBinding() {
    return BindingBuilder.bind(dlqQueue()).to(dlxExchange()).with("dlq.order");
  }
}
