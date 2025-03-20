package com.example.pharmacystore.drug.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "categories",
    uniqueConstraints = {
      @UniqueConstraint(name = "category_unique_constraint", columnNames = "categoryName")
    })
@RequiredArgsConstructor
@Getter
@Setter
public class CategoryModel {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(
      name = "category_sequence",
      sequenceName = "category_sequence",
      allocationSize = 1)
  private long id;

  @Column(name = "category_name", nullable = false)
  private String categoryName;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<DrugModel> drugs = new ArrayList<>();

  public void addDrug(DrugModel drugModel) {
    this.drugs.add(drugModel);
  }

  public void removeDrug(DrugModel drugModel) {
    this.drugs.remove(drugModel);
  }
}
