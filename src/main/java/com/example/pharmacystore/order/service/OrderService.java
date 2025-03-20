package com.example.pharmacystore.order.service;

import com.example.pharmacystore.drug.model.DrugModel;
import com.example.pharmacystore.drug.repository.DrugRepository;
import com.example.pharmacystore.exceptions.DrugAvailbleQuantityExcetion;
import com.example.pharmacystore.exceptions.DrugNotFoundException;
import com.example.pharmacystore.exceptions.OrderCancellationNotAllowedException;
import com.example.pharmacystore.order.OrderStatus;
import com.example.pharmacystore.order.dots.OrderDto;
import com.example.pharmacystore.order.dots.OrderItemDto;
import com.example.pharmacystore.order.dots.OrderResponseDto;
import com.example.pharmacystore.order.model.OrderItemModel;
import com.example.pharmacystore.order.model.OrderModel;
import com.example.pharmacystore.order.repository.OrderItemRepository;
import com.example.pharmacystore.order.repository.OrderRepository;
import com.example.pharmacystore.user.model.UserMoldel;
import com.example.pharmacystore.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@AllArgsConstructor
public class OrderService {

  private final OrderItemRepository orderItemRepository;
  private OrderRepository orderRepository;
  private UserRepository userRepository;
  private DrugRepository drugRepository;
  private HelperService helperService;

  public OrderResponseDto createOrder(OrderDto orderDto) {
    OrderModel orderModel = new OrderModel();

    int totalPrice = 0;

    UserMoldel userMoldel =
        userRepository
            .findById(orderDto.getUser_id())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    orderModel.setUser(userMoldel);
    orderModel.setStatus(orderDto.getStatus());

    for (OrderItemDto orderItemDto : orderDto.getOrderItems()) {
      OrderItemModel orderItemModel = new OrderItemModel();

      DrugModel drugModel =
          drugRepository
              .findById(orderItemDto.getDrug_id())
              .orElseThrow(() -> new DrugNotFoundException("the Drug is not found"));

      totalPrice += orderItemDto.getQuantity() * drugModel.getPrice();

      if (drugModel.getAvailableQuantity() < orderItemDto.getQuantity()) {
        throw new DrugAvailbleQuantityExcetion("there is no enough quantity");
      }

      orderItemModel.setDrug(drugModel);
      orderItemModel.setQuantity(orderItemDto.getQuantity());

      orderModel.addOrderItem(orderItemModel);
      drugModel.setAvailableQuantity(drugModel.getAvailableQuantity() - orderItemDto.getQuantity());
    }

    orderModel.setTotalPrice(totalPrice);

    userMoldel.addOrder(orderModel);
    userRepository.save(userMoldel);

    return helperService.convertOrderModelToOrderResponseDto(orderModel);
  }

  public OrderResponseDto updateOrder(OrderDto orderDto, Long orderId) {

    OrderModel orderModel =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new DrugNotFoundException("the order is not found"));

    if (orderModel.getStatus() != OrderStatus.PENDING) {
      throw new OrderCancellationNotAllowedException("can not be upadated because it is accepted");
    }

    int totalPrice = 0;

    for (OrderItemDto orderItemDto : orderDto.getOrderItems()) {

      Optional<OrderItemModel> orderItem =
          orderItemRepository.findOrderItem(orderId, orderItemDto.getDrug_id());

      if (!orderItem.isPresent()) {
        DrugModel drugModel =
            drugRepository
                .findById(orderItemDto.getDrug_id())
                .orElseThrow(() -> new DrugNotFoundException("the drug is not found"));

        OrderItemModel orderItemModel = new OrderItemModel();
        orderItemModel.setOrder(orderModel);
        orderItemModel.setDrug(drugModel);

        if (drugModel.getAvailableQuantity() < orderItemDto.getQuantity()) {
          throw new DrugAvailbleQuantityExcetion("there is no enough quantity");
        }

        orderItemModel.setQuantity(orderItemDto.getQuantity());
        drugModel.setAvailableQuantity(
            drugModel.getAvailableQuantity() - orderItemDto.getQuantity());
        totalPrice += orderItemDto.getQuantity() * drugModel.getPrice();
        orderModel.addOrderItem(orderItemModel);

      } else {
        int difference = orderItemDto.getQuantity() - orderItem.get().getQuantity();

        if (difference > 0 && orderItem.get().getDrug().getAvailableQuantity() < difference) {
          throw new DrugAvailbleQuantityExcetion("there is no enough quantity");
        }

        int newQuantity =
            difference > 0
                ? orderItem.get().getDrug().getAvailableQuantity() - difference
                : orderItem.get().getDrug().getAvailableQuantity() + Math.abs(difference);

        totalPrice += orderItemDto.getQuantity() * orderItem.get().getDrug().getPrice();
        orderItem.get().setQuantity(orderItemDto.getQuantity());
        orderItem.get().getDrug().setAvailableQuantity(newQuantity);
      }
    }

    helperService.removeOrderItemFromOrder(orderDto, orderModel);

    orderModel.setTotalPrice(totalPrice);
    orderRepository.save(orderModel);

    return helperService.convertOrderModelToOrderResponseDto(orderModel);
  }

  public Page<OrderModel> getMyOrder(Pageable pageable) {
    UserDetails userDetails =
        (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String email = userDetails.getUsername();
    Optional<UserMoldel> byEmail = userRepository.findByEmail(email);
    Page orderModelByEmail = orderRepository.findByUserId(byEmail.get().getId(), pageable);
    return orderModelByEmail;
  }

  public OrderResponseDto cancelOrder(Long orderId) {
    OrderModel orderModel =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new DrugNotFoundException("the order is not found"));

    if (orderModel.getStatus() != OrderStatus.PENDING) {
      throw new OrderCancellationNotAllowedException("can not be canceled");
    }

    orderModel.setStatus(OrderStatus.CANCELED);
    orderRepository.save(orderModel);

    return helperService.convertOrderModelToOrderResponseDto(orderModel);
  }

  public OrderResponseDto updateStatus(Long orderId, OrderStatus status) {

    OrderModel orderModel =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new DrugNotFoundException("the order is not found"));

    if (orderModel.getStatus() != OrderStatus.PENDING) {
      throw new OrderCancellationNotAllowedException("can not be canceled");
    }

    orderModel.setStatus(status);

    orderRepository.save(orderModel);
    return helperService.convertOrderModelToOrderResponseDto(orderModel);
  }
}
