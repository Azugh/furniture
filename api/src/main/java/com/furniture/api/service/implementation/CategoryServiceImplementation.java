package com.furniture.api.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.furniture.api.service.CategoryService;
import com.furniture.api.service.RestaurantService;
import com.furniture.core.exception.RestaurantException;
import com.furniture.core.model.Category;
import com.furniture.core.model.Restaurant;
import com.furniture.core.repository.CategoryRepository;

@Service
public class CategoryServiceImplementation implements CategoryService {

  private RestaurantService restaurantService;
  private CategoryRepository categoryRepository;

  CategoryServiceImplementation(RestaurantService restaurantService, CategoryRepository categoryRepository) {
    this.restaurantService = restaurantService;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Category createCategory(String name, Long userId) throws RestaurantException {
    Restaurant restaurant = restaurantService.getRestaurantsByUserId(userId);
    Category createdCategory = new Category();

    createdCategory.setName(name);
    createdCategory.setRestaurant(restaurant);
    return categoryRepository.save(createdCategory);
  }

  @Override
  public List<Category> findCategoryByRestaurantId(Long id) throws RestaurantException {
    // Restaurant restaurant = restaurantService.findRestaurantById(id);
    return categoryRepository.findByRestaurantId(id);
  }

  @Override
  public Category findCategoryById(Long id) throws RestaurantException {
    Optional<Category> opt = categoryRepository.findById(id);

    if (opt.isEmpty()) {
      throw new RestaurantException("category not exist with id " + id);
    }

    return opt.get();
  }

}
