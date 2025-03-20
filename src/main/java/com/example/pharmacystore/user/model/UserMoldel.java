package com.example.pharmacystore.user.model;

import com.example.pharmacystore.favorite.FavoriteModel;
import com.example.pharmacystore.order.model.OrderModel;
import com.example.pharmacystore.user.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {@UniqueConstraint(name = "user_email_unique", columnNames = "email")})
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class UserMoldel {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
  @Column(name = "id", nullable = false, updatable = false)
  private long id;

  @NonNull private String name;

  @NonNull private String email;

  @NonNull private String password;

  @Column(name = "img_url", nullable = true)
  private String imageUrl;

  @NonNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @NonNull
  @Column(name = "is_verified", nullable = false)
  private boolean isVerified = false;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<FavoriteModel> favorites;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<OrderModel> orders;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  VerificationTokenModel verificationToken;

  public void addFavorite(FavoriteModel favorite) {
    favorites.add(favorite);
  }

  public void removeFavorite(FavoriteModel favorite) {
    this.favorites.remove(favorite);
  }

  public void addOrder(OrderModel order) {
    orders.add(order);
  }

  public void removeOrder(OrderModel order) {
    orders.remove(order);
  }
}
