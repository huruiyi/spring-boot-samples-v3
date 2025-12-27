package vip.fairy.springbootrabbitmqdemo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
  private String userId;
  private String orderId;
}
