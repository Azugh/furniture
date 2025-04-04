package com.furniture.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.furniture.core.model.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {

  List<Food> findByRestaurantId(Long restaurantId);

  @Query("SELECT f FROM Food f WHERE "
      + "f.name LIKE %:keyword% OR "
      + "f.category.name LIKE %:keyword% AND "
      + "f.restaurant!=null")
  List<Food> searchByNameOrCategory(@Param("keyword") String keyword);
}
