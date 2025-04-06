package com.furniture.api.service;

import java.util.List;

import com.furniture.core.exception.ReviewException;
import com.furniture.core.model.Review;
import com.furniture.core.model.User;
import com.furniture.core.request.ReviewRequest;

public interface ReviewService {

  public Review submitReview(ReviewRequest review, User user);

  public void deleteReview(Long reviewId) throws ReviewException;

  public double calculateAverageRating(List<Review> reviews);
}
