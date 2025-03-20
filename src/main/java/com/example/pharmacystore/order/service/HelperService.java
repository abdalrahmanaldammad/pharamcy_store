package com.example.pharmacystore.order.service;

import com.example.pharmacystore.drug.dtos.DrugDto;
import com.example.pharmacystore.order.dots.OrderDto;
import com.example.pharmacystore.order.dots.OrderItemDto;
import com.example.pharmacystore.order.dots.OrderResponseDto;
import com.example.pharmacystore.order.model.OrderModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HelperService {

  public void removeOrderItemFromOrder(OrderDto orderDto, OrderModel orderModel) {
    orderModel
        .getOrderItems()
        .forEach(
            (orderItemModel) -> {
              boolean found =
                  orderDto.getOrderItems().stream()
                      .map(orderItemDto -> orderItemDto.getDrug_id())
                      .anyMatch(aLong -> aLong == orderItemModel.getDrug().getId());

              if (!found) {
                orderItemModel
                    .getDrug()
                    .setAvailableQuantity(
                        orderItemModel.getDrug().getAvailableQuantity()
                            + orderItemModel.getQuantity());
                orderModel.removeOrderItem(orderItemModel);
              }
            });
  }

  public OrderResponseDto convertOrderModelToOrderResponseDto(OrderModel orderModel) {
    return new OrderResponseDto(
        orderModel.getId(),
        orderModel.getStatus(),
        orderModel.getOrderDate(),
        orderModel.getLastUpdate(),
        orderModel.getOrderItems().stream()
            .map(
                (orderItemModel ->
                    new OrderItemDto(
                        orderItemModel.getId(),
                        new DrugDto(
                            orderItemModel.getDrug().getId(),
                            orderItemModel.getDrug().getCommercialName(),
                            orderItemModel.getDrug().getAvailableQuantity(),
                            orderItemModel.getDrug().getManufacturer(),
                            orderItemModel.getDrug().getExpirationDate(),
                            orderItemModel.getDrug().getPrice(),
                            orderItemModel.getDrug().getCategory().getCategoryName()),
                        orderModel.getId(),
                        orderItemModel.getQuantity())))
            .collect(Collectors.toList()));
  }
}
