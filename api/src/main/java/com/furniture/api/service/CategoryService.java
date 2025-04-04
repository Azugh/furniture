package com.furniture.api.service;

import java.util.List;

import com.furniture.core.exception.RestaurantException;
import com.furniture.core.model.Category;

public interface CategoryService {

  public Category createCategory(String name, Long userId) throws RestaurantException;

  public List<Category> findCategoryByRestaurantId(Long restaurantId) throws RestaurantException;

  public Category findCategoryById(Long id) throws RestaurantException;
}
