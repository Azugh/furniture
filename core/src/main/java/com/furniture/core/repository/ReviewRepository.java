package com.furniture.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.furniture.core.model.Furniture;
import com.furniture.core.model.Review;
import com.furniture.core.model.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
  Page<Review> findByFurniture(Furniture furniture, Pageable pageable);

  List<Review> findByFurnitureOrderByCreatedAtDesc(Furniture furniture);

  boolean existsByUserAndFurniture(User user, Furniture furniture);
}
