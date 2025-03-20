package com.example.pharmacystore.drug.model;

import com.example.pharmacystore.favorite.FavoriteModel;
import com.example.pharmacystore.order.model.OrderItemModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "drug")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DrugModel {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "drug_sequence", sequenceName = "drug_sequence", allocationSize = 1)
  @NonNull
  private long id;

  @NonNull
  @Column(name = "commercial_name", nullable = false)
  private String commercialName;

  @NonNull
  @Column(name = "available_quantity", nullable = false)
  private int availableQuantity;

  @NonNull private String manufacturer;

  @NonNull private int price;

  @NonNull
  @Column(name = "expiration_date")
  private LocalDate expirationDate;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "category_id", nullable = false)
  private CategoryModel category;

  @OneToMany(mappedBy = "drug", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<FavoriteModel> favorites;

  @OneToMany(mappedBy = "drug", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<OrderItemModel> orderItems;

  public void addFavorite(FavoriteModel favorite) {
    this.favorites.add(favorite);
  }

  public void removeFavorite(FavoriteModel favorite) {
    this.favorites.remove(favorite);
  }

  public void addOrderItem(OrderItemModel orderItem) {
    this.orderItems.add(orderItem);
  }

  public void removeOrderItem(OrderItemModel orderItem) {
    this.orderItems.remove(orderItem);
  }
}
