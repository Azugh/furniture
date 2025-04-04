package com.furniture.core.request;

import java.util.List;
import java.util.Locale.Category;

import com.furniture.core.model.IngredientItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFoodRequest {

  private String name;
  private String description;
  private Long price;
  private Category category;
  private List<String> image;
  private Long restaurantId;
  private boolean vegetarian;
  private boolean seasonal;
  private List<IngredientItem> ingredients;
}
