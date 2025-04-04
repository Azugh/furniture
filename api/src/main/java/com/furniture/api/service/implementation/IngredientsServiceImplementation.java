package com.furniture.api.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.furniture.api.service.IngredientsService;
import com.furniture.api.service.RestaurantService;
import com.furniture.core.exception.RestaurantException;
import com.furniture.core.model.IngredientCategory;
import com.furniture.core.model.IngredientItem;
import com.furniture.core.model.Restaurant;
import com.furniture.core.repository.IngredientsCategoryRepository;
import com.furniture.core.repository.IngredientsItemRepository;

@Service
public class IngredientsServiceImplementation implements IngredientsService {

  private final IngredientsCategoryRepository ingredientsCategoryRepo;
  private final IngredientsItemRepository ingredientsItemRepository;
  private final RestaurantService restaurantService;

  IngredientsServiceImplementation(IngredientsCategoryRepository ingredientsCategoryRepo,
      IngredientsItemRepository ingredientsItemRepository, RestaurantService restaurantService) {
    this.ingredientsCategoryRepo = ingredientsCategoryRepo;
    this.ingredientsItemRepository = ingredientsItemRepository;
    this.restaurantService = restaurantService;
  }

  @Override
  public IngredientCategory createIngredientsCategory(
      String name, Long restaurantId) throws RestaurantException {

    IngredientCategory isExist = ingredientsCategoryRepo
        .findByRestaurantIdAndNameIgnoreCase(restaurantId, name);

    if (isExist != null) {
      return isExist;
    }

    Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

    IngredientCategory ingredientCategory = new IngredientCategory();
    ingredientCategory.setRestaurant(restaurant);
    ingredientCategory.setName(name);

    IngredientCategory createdCategory = ingredientsCategoryRepo.save(ingredientCategory);

    return createdCategory;
  }

  @Override
  public IngredientCategory findIngredientsCategoryById(Long id) throws Exception {
    Optional<IngredientCategory> opt = ingredientsCategoryRepo.findById(id);
    if (opt.isEmpty()) {
      throw new Exception("ingredient category not found");
    }
    return opt.get();
  }

  @Override
  public List<IngredientCategory> findIngredientsCategoryByRestaurantId(Long id) throws Exception {
    return ingredientsCategoryRepo.findByRestaurantId(id);
  }

  @Override
  public List<IngredientItem> findRestaurantsIngredients(Long restaurantId) {

    return ingredientsItemRepository.findByRestaurantId(restaurantId);
  }

  @Override
  public IngredientItem createIngredientsItem(Long restaurantId,
      String ingredientName, Long ingredientCategoryId) throws Exception {

    IngredientCategory category = findIngredientsCategoryById(ingredientCategoryId);

    IngredientItem isExist = ingredientsItemRepository.findByRestaurantIdAndNameIngoreCase(restaurantId,
        ingredientName, category.getName());
    if (isExist != null) {
      System.out.println("is exists-------- item");
      return isExist;
    }

    Restaurant restaurant = restaurantService.findRestaurantById(
        restaurantId);
    IngredientItem item = new IngredientItem();
    item.setName(ingredientName);
    item.setRestaurant(restaurant);
    item.setCategory(category);

    IngredientItem savedIngredients = ingredientsItemRepository.save(item);
    category.getIngredients().add(savedIngredients);

    return savedIngredients;
  }

  @Override
  public IngredientItem updateStoke(Long id) throws Exception {
    Optional<IngredientItem> item = ingredientsItemRepository.findById(id);
    if (item.isEmpty()) {
      throw new Exception("ingredient not found with id " + item);
    }
    IngredientItem ingredient = item.get();
    ingredient.setInStoke(!ingredient.isInStoke());
    return ingredientsItemRepository.save(ingredient);
  }

}
