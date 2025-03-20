package com.example.pharmacystore.order;

import com.example.pharmacystore.order.dots.OrderDto;
import com.example.pharmacystore.order.dots.OrderResponseDto;
import com.example.pharmacystore.order.model.OrderModel;
import com.example.pharmacystore.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

  private OrderService orderService;

  @PostMapping("/creat")
  public ResponseEntity<OrderResponseDto> creatOrder(@RequestBody OrderDto orderDto) {
    OrderResponseDto order = orderService.createOrder(orderDto);
    return ResponseEntity.ok(order);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<OrderResponseDto> updateOrder(
      @RequestBody OrderDto orderDto, @PathVariable Long id) {
    OrderResponseDto orderResponseDto = orderService.updateOrder(orderDto, id);
    return ResponseEntity.ok(orderResponseDto);
  }

  @GetMapping("/my-order")
  public ResponseEntity<Page<OrderModel>> getMyOrder(Pageable pageable) {
    Page<OrderModel> myOrder = orderService.getMyOrder(pageable);
    return ResponseEntity.ok(myOrder);
  }

  @PutMapping("/cancel/{id}")
  public ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable Long id) {
    OrderResponseDto orderResponseDto = orderService.cancelOrder(id);
    return ResponseEntity.ok(orderResponseDto);
  }

  @PutMapping("/update-status/{id]")
  public ResponseEntity<OrderResponseDto> updateOrderStatus(@RequestBody OrderStatus orderStatus,@PathVariable Long id) {}
}
