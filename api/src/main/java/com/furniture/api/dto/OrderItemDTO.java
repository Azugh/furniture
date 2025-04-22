package com.furniture.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemDTO {
  private Long id;

  @NotNull
  private Long furnitureId;

  @NotNull
  @Positive
  private Integer quantity;

  @NotNull
  @Positive
  private Double price;
}
