package com.example.pharmacystore.order.model;

import com.example.pharmacystore.drug.model.DrugModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(
    name = "order_items",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"order_id", "drug_id"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemModel {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(
      name = "order_item_sequence",
      sequenceName = "order_item_sequence",
      allocationSize = 1)
  private long id;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "durg_id")
  private DrugModel drug;

  @ManyToOne
  @JoinColumn(name = "order_id")
  @JsonIgnore
  private OrderModel order;

  private int quantity;
}
