package com.furniture.core.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.furniture.core.model.Category;
import com.furniture.core.model.Furniture;

public interface FurnitureService {
  Furniture addFurniture(Furniture furniture);

  Furniture updateFurniture(Furniture furniture);

  void deleteFurniture(Long id);

  Furniture getFurnitureById(Long id);

  Page<Furniture> getAllFurniture(int page, int size);

  Page<Furniture> getFurnitureByCategory(Long categoryId, int page, int size);

  Page<Furniture> searchFurniture(String query, int page, int size);

  Category addCategory(Category category);

  List<Category> getAllCategories();

  void deleteCategory(Long id);
}
