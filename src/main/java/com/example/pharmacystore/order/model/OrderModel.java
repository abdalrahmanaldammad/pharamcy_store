package com.example.pharmacystore.order.model;

import com.example.pharmacystore.order.OrderStatus;
import com.example.pharmacystore.user.model.UserMoldel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class OrderModel {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1)
  private long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private UserMoldel user;

  @CreationTimestamp
  @Column(name = "order_date")
  private LocalDateTime orderDate = LocalDateTime.now();

  @UpdateTimestamp
  @Column(name = "last_update")
  private LocalDateTime lastUpdate = LocalDateTime.now();

  @Enumerated(EnumType.STRING)
  @Column(name = "order_status")
  private OrderStatus status;

  @Column(name = "total_price")
  @NonNull
  private int totalPrice;

  @OneToMany(
      mappedBy = "order",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<OrderItemModel> orderItems = new ArrayList<>();

  public void addOrderItem(OrderItemModel orderItem) {
    orderItem.setOrder(this);
    orderItems.add(orderItem);
  }

  public void removeOrderItem(OrderItemModel orderItem) {
    orderItem.setOrder(null);
    boolean remove = this.orderItems.remove(orderItem);
  }
}
