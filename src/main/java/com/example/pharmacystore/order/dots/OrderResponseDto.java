package com.example.pharmacystore.order.dots;

import com.example.pharmacystore.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class OrderResponseDto {
  private Long id;

  private OrderStatus status;

  private LocalDateTime orderDate;

  private LocalDateTime updateDate;

  private List<OrderItemDto> orderItems;
}
