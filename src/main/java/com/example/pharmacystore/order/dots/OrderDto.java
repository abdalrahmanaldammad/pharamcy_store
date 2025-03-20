package com.example.pharmacystore.order.dots;

import com.example.pharmacystore.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
  private Long user_id;
  private OrderStatus status = OrderStatus.PENDING;
  private List<OrderItemDto> orderItems;
}
