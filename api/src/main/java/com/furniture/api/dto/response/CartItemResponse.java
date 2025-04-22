package com.furniture.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
  private Long id;
  private FurnitureResponse furniture;
  private Integer quantity;
  private Double price;
}
