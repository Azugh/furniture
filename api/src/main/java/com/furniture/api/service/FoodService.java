package com.furniture.api.service;

import java.util.List;

import com.furniture.core.exception.FoodException;
import com.furniture.core.exception.RestaurantException;
import com.furniture.core.model.Category;
import com.furniture.core.model.Food;
import com.furniture.core.model.Restaurant;
import com.furniture.core.request.CreateFoodRequest;

public interface FoodService {

  public Food createFood(CreateFoodRequest req, Category category,
      Restaurant restaurant) throws FoodException, RestaurantException;

  void deleteFood(Long foodId) throws FoodException;

  public List<Food> getRestaurantsFood(Long restaurantId,
      boolean isVegetarian, boolean isNonveg, boolean isSeasonal, String foodCategory) throws FoodException;

  public List<Food> searchFood(String keyword);

  public Food findFoodById(Long foodId) throws FoodException;

  public Food updateAvailibilityStatus(Long foodId) throws FoodException;
}
