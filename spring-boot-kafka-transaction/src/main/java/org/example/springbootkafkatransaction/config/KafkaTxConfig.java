package org.example.springbootkafkatransaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class KafkaTxConfig {

  // ✅ 关键：声明为 @Primary，成为Spring默认事务管理器
  @Bean
  @Primary
  public KafkaTransactionManager<String, String> kafkaTransactionManager(
      ProducerFactory<String, String> producerFactory) {
    KafkaTransactionManager<String, String> txManager =
        new KafkaTransactionManager<>(producerFactory);
    // 可选：设置事务超时（必须 > 业务处理时间）
    txManager.setDefaultTimeout(60);
    return txManager;
  }

  // ✅ 修正：不再设置事务管理器！移除所有 setTransactionManager 调用
  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
      ConsumerFactory<String, String> consumerFactory) {

    ConcurrentKafkaListenerContainerFactory<String, String> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);

    // ⚠️ 重要：AckMode 必须设为 MANUAL（由事务管理偏移量）
    // Spring Kafka 3.2+ 会检测 @Transactional 自动处理偏移量提交
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

    // 其他可选配置
    factory.setBatchListener(false);
    factory.setRecordFilterStrategy(record -> false); // 如需过滤
    return factory;
  }

  // 🔁 场景扩展：若需同时管理 DB + Kafka 事务（ChainedTx）
    /*
    @Bean
    @Primary
    public ChainedKafkaTransactionManager<String, String> chainedTxManager(
            DataSourceTransactionManager dbTxManager,
            KafkaTransactionManager<String, String> kafkaTxManager) {
        return new ChainedKafkaTransactionManager<>(dbTxManager, kafkaTxManager);
    }
    */
}