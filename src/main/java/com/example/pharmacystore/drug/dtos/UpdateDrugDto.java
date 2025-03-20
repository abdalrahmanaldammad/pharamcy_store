package com.example.pharmacystore.drug.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UpdateDrugDto {

  private String commercialName;

  private Integer availableQuantity;

  private String manufacturer;

  private LocalDate expirationDate;

  private Integer price;

  private String categoryName;
}
