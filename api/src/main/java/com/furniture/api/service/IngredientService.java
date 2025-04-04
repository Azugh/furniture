package com.furniture.api.service;

import java.util.List;

import com.furniture.core.exception.RestaurantException;
import com.furniture.core.model.IngredientCategory;
import com.furniture.core.model.IngredientItem;

public interface IngredientService {

  public IngredientCategory createIngredientsCategory(
      String name, Long restaurantId) throws RestaurantException;

  public IngredientCategory findIngredientsCategoryById(Long id) throws Exception;

  public List<IngredientCategory> findIngredientsCategoryByRestaurantId(Long id) throws Exception;

  public List<IngredientItem> findRestaurantsIngredients(
      Long restaurantId);

  public IngredientItem createIngredientsItem(Long restaurantId,
      String ingredientName, Long ingredientCategoryId) throws Exception;

  public IngredientItem updateStoke(Long id) throws Exception;

}
