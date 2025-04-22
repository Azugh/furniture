package com.furniture.core.service;

import java.util.List;

import com.furniture.core.model.Review;

public interface ReviewService {
  Review addReview(Review review);

  List<Review> getReviewsByFurniture(Long furnitureId);

  void deleteReview(Long reviewId);
}
