package com.example.pharmacystore.order.dots;

import com.example.pharmacystore.drug.dtos.DrugDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDto {

  @NotBlank private long drug_id;

  private DrugDto drugDto;

  @NotNull private Long order_id;

  @NotBlank private int quantity;
}
