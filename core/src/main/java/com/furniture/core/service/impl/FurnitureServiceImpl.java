package com.furniture.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.furniture.core.model.Category;
import com.furniture.core.model.Furniture;
import com.furniture.core.repository.CategoryRepository;
import com.furniture.core.repository.FurnitureRepository;
import com.furniture.core.service.FurnitureService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FurnitureServiceImpl implements FurnitureService {
  private final FurnitureRepository furnitureRepository;
  private final CategoryRepository categoryRepository;
  private static final int PAGE_SIZE = 15;

  @Override
  public Furniture addFurniture(Furniture furniture) {
    return furnitureRepository.save(furniture);
  }

  @Override
  public Furniture updateFurniture(Furniture furniture) {
    Furniture existing = furnitureRepository.findById(furniture.getId())
        .orElseThrow(() -> new RuntimeException("Furniture not found"));
    existing.setName(furniture.getName());
    existing.setDescription(furniture.getDescription());
    existing.setPrice(furniture.getPrice());
    existing.setStockQuantity(furniture.getStockQuantity());
    existing.setImageUrl(furniture.getImageUrl());
    existing.setCategory(furniture.getCategory());
    return furnitureRepository.save(existing);
  }

  @Override
  public void deleteFurniture(Long id) {
    furnitureRepository.deleteById(id);
  }

  @Override
  public Furniture getFurnitureById(Long id) {
    return furnitureRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Furniture not found"));
  }

  @Override
  public Page<Furniture> getAllFurniture(int page, int size) {
    return furnitureRepository.findAll(PageRequest.of(page, size));
  }

  @Override
  public Page<Furniture> getFurnitureByCategory(Long categoryId, int page, int size) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new RuntimeException("Category not found"));
    return furnitureRepository.findByCategory(category, PageRequest.of(page, size));
  }

  @Override
  public Page<Furniture> searchFurniture(String query, int page, int size) {
    return furnitureRepository.findByNameContainingIgnoreCase(query, PageRequest.of(page, size));
  }

  @Override
  public Category addCategory(Category category) {
    return categoryRepository.save(category);
  }

  @Override
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  @Override
  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }
}
