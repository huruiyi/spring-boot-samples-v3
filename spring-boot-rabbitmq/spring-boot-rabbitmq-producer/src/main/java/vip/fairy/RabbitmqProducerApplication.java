package vip.fairy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vip.fairy.model.Order;
import vip.fairy.service.MessageProducerService;

@SpringBootApplication
public class RabbitmqProducerApplication implements CommandLineRunner {

  private final MessageProducerService messageProducerService;

  @Autowired
  public RabbitmqProducerApplication(MessageProducerService messageProducerService) {
    this.messageProducerService = messageProducerService;
  }

  public static void main(String[] args) {
    SpringApplication.run(RabbitmqProducerApplication.class, args);
  }

  @Override
  public void run(String... args) {
    long id = 1L;
    while (true) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        Order order = Order.builder()
            .userId("user-huruiyi")
            .orderId("order-" + (id++))
            .build();
        messageProducerService.sendReliableMessage(objectMapper.writeValueAsString(order));
        Thread.sleep(1000);
      } catch (InterruptedException | JsonProcessingException e) {
        e.printStackTrace();
      }
    }
  }

}
