package com.furniture.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.furniture.core.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  List<Category> findByRestaurantId(Long id);
}
