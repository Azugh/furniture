package com.furniture.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.furniture.api.dto.CategoryDTO;
import com.furniture.api.dto.FurnitureDTO;
import com.furniture.core.model.Category;
import com.furniture.core.model.Furniture;
import com.furniture.core.service.FurnitureService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/furniture")
@RequiredArgsConstructor
public class FurnitureController {
  private final FurnitureService furnitureService;
  private static final int DEFAULT_PAGE_SIZE = 15;

  @GetMapping
  public ResponseEntity<Page<FurnitureDTO>> getAllFurniture(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "15") int size) {
    if (size > DEFAULT_PAGE_SIZE) {
      size = DEFAULT_PAGE_SIZE;
    }

    Page<Furniture> furniturePage = furnitureService.getAllFurniture(page, size);
    Page<FurnitureDTO> dtoPage = furniturePage.map(this::convertToFurnitureDTO);
    return ResponseEntity.ok(dtoPage);
  }

  @GetMapping("/category/{categoryId}")
  public ResponseEntity<Page<FurnitureDTO>> getFurnitureByCategory(
      @PathVariable Long categoryId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "15") int size) {
    if (size > DEFAULT_PAGE_SIZE) {
      size = DEFAULT_PAGE_SIZE;
    }

    Page<Furniture> furniturePage = furnitureService.getFurnitureByCategory(categoryId, page, size);
    Page<FurnitureDTO> dtoPage = furniturePage.map(this::convertToFurnitureDTO);
    return ResponseEntity.ok(dtoPage);
  }

  @GetMapping("/search")
  public ResponseEntity<Page<FurnitureDTO>> searchFurniture(
      @RequestParam String query,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "15") int size) {
    if (size > DEFAULT_PAGE_SIZE) {
      size = DEFAULT_PAGE_SIZE;
    }

    Page<Furniture> furniturePage = furnitureService.searchFurniture(query, page, size);
    Page<FurnitureDTO> dtoPage = furniturePage.map(this::convertToFurnitureDTO);
    return ResponseEntity.ok(dtoPage);
  }

  @GetMapping("/{id}")
  public ResponseEntity<FurnitureDTO> getFurnitureById(@PathVariable Long id) {
    Furniture furniture = furnitureService.getFurnitureById(id);
    return ResponseEntity.ok(convertToFurnitureDTO(furniture));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<FurnitureDTO> addFurniture(@Valid @RequestBody FurnitureDTO furnitureDTO) {
    Furniture furniture = convertToFurnitureEntity(furnitureDTO);
    Furniture savedFurniture = furnitureService.addFurniture(furniture);
    return ResponseEntity.ok(convertToFurnitureDTO(savedFurniture));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<FurnitureDTO> updateFurniture(
      @PathVariable Long id,
      @Valid @RequestBody FurnitureDTO furnitureDTO) {
    furnitureDTO.setId(id);
    Furniture furniture = convertToFurnitureEntity(furnitureDTO);
    Furniture updatedFurniture = furnitureService.updateFurniture(furniture);
    return ResponseEntity.ok(convertToFurnitureDTO(updatedFurniture));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteFurniture(@PathVariable Long id) {
    furnitureService.deleteFurniture(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/categories")
  public ResponseEntity<List<CategoryDTO>> getAllCategories() {
    List<Category> categories = furnitureService.getAllCategories();
    List<CategoryDTO> categoryDTOs = categories.stream().map(this::convertToCategoryDTO).toList();
    return ResponseEntity.ok(categoryDTOs);
  }

  @PostMapping("/categories")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
    Category category = convertToCategoryEntity(categoryDTO);
    Category savedCategory = furnitureService.addCategory(category);
    return ResponseEntity.ok(convertToCategoryDTO(savedCategory));
  }

  @DeleteMapping("/categories/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    furnitureService.deleteCategory(id);
    return ResponseEntity.noContent().build();
  }

  private FurnitureDTO convertToFurnitureDTO(Furniture furniture) {
    FurnitureDTO dto = new FurnitureDTO();
    dto.setId(furniture.getId());
    dto.setName(furniture.getName());
    dto.setDescription(furniture.getDescription());
    dto.setPrice(furniture.getPrice());
    dto.setStockQuantity(furniture.getStockQuantity());
    dto.setImages(furniture.getImages());
    dto.setCategoryId(furniture.getCategory().getId());
    return dto;
  }

  private Furniture convertToFurnitureEntity(FurnitureDTO dto) {
    Furniture furniture = new Furniture();
    furniture.setId(dto.getId());
    furniture.setName(dto.getName());
    furniture.setDescription(dto.getDescription());
    furniture.setPrice(dto.getPrice());
    furniture.setStockQuantity(dto.getStockQuantity());
    furniture.setImages(dto.getImages());

    Category category = new Category();
    category.setId(dto.getCategoryId());
    furniture.setCategory(category);

    return furniture;
  }

  private CategoryDTO convertToCategoryDTO(Category category) {
    CategoryDTO dto = new CategoryDTO();
    dto.setId(category.getId());
    dto.setName(category.getName());
    dto.setDescription(category.getDescription());
    return dto;
  }

  private Category convertToCategoryEntity(CategoryDTO dto) {
    Category category = new Category();
    category.setId(dto.getId());
    category.setName(dto.getName());
    category.setDescription(dto.getDescription());
    return category;
  }
}
