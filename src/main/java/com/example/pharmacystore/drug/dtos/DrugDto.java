package com.example.pharmacystore.drug.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class DrugDto {

  private Long id;

  @NotNull private String commercialName;

  @NotNull private int availableQuantity;

  @NotNull private String manufacturer;

  @NotNull private LocalDate expirationDate;

  @NotNull private int price;

  @NotNull private String categoryName;
}
