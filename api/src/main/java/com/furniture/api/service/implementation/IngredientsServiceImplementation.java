package com.furniture.api.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.furniture.api.service.IngredientService;
import com.furniture.api.service.RestaurantService;
import com.furniture.core.exception.RestaurantException;
import com.furniture.core.model.IngredientCategory;
import com.furniture.core.model.IngredientsItem;
import com.furniture.core.model.Restaurant;
import com.furniture.core.repository.IngredientItemRepository;
import com.furniture.core.repository.IngredientsCategoryRepository;

@Service
public class IngredientsServiceImplementation implements IngredientService {

  private final IngredientsCategoryRepository ingredientsCategoryRepo;
  private final IngredientItemRepository ingredientItemRepository;
  private final RestaurantService restaurantService;

  IngredientsServiceImplementation(IngredientsCategoryRepository ingredientsCategoryRepo,
      IngredientItemRepository ingredientsItemRepository, RestaurantService restaurantService) {
    this.ingredientsCategoryRepo = ingredientsCategoryRepo;
    this.ingredientItemRepository = ingredientsItemRepository;
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
  public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {

    return ingredientItemRepository.findByRestaurantId(restaurantId);
  }

  @Override
  public IngredientsItem createIngredientsItem(Long restaurantId,
                                               String ingredientName, Long ingredientCategoryId) throws Exception {

    IngredientCategory category = findIngredientsCategoryById(ingredientCategoryId);

    IngredientsItem isExist = ingredientItemRepository.findByRestaurantIdAndNameIgnoreCase(restaurantId,
        ingredientName, category.getName());
    if (isExist != null) {
      System.out.println("is exists-------- item");
      return isExist;
    }

    Restaurant restaurant = restaurantService.findRestaurantById(
        restaurantId);
    IngredientsItem item = new IngredientsItem();
    item.setName(ingredientName);
    item.setRestaurant(restaurant);
    item.setCategory(category);

    IngredientsItem savedIngredients = ingredientItemRepository.save(item);
    category.getIngredients().add(savedIngredients);

    return savedIngredients;
  }

  @Override
  public IngredientsItem updateStoke(Long id) throws Exception {
    Optional<IngredientsItem> item = ingredientItemRepository.findById(id);
    if (item.isEmpty()) {
      throw new Exception("ingredient not found with id " + item);
    }
    IngredientsItem ingredient = item.get();
    ingredient.setInStoke(!ingredient.isInStoke());
    return ingredientItemRepository.save(ingredient);
  }

}
