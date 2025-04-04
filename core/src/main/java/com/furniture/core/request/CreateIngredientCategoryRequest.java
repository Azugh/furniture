package com.furniture.core.request;

import lombok.Data;

@Data
public class CreateIngredientCategoryRequest {

  private Long restaurantId;
  private String name;
}
