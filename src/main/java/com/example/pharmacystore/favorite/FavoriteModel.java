package com.example.pharmacystore.favorite;

import com.example.pharmacystore.drug.model.DrugModel;
import com.example.pharmacystore.user.model.UserMoldel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "favorites",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "favorite_unique_constraint",
          columnNames = {"durg_id", "user_id"})
    })
@Getter
@Setter
public class FavoriteModel {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(
      name = "favorite_sequence",
      sequenceName = "favorite_sequence",
      allocationSize = 1)
  private long id;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "drug_id", nullable = false)
  private DrugModel drug;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "user_id", nullable = false)
  private UserMoldel user;
}
