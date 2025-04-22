package com.furniture.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.furniture.core.model.Category;
import com.furniture.core.model.Furniture;

import java.util.List;

@Repository
public interface FurnitureRepository extends JpaRepository<Furniture, Long> {
  Page<Furniture> findByCategory(Category category, Pageable pageable);

  List<Furniture> findTop10ByOrderByCreatedAtDesc();
}
