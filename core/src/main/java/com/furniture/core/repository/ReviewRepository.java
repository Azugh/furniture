package com.furniture.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.furniture.core.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
