package com.furniture.api.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.furniture.api.service.ReviewService;
import com.furniture.core.exception.ReviewException;
import com.furniture.core.model.Restaurant;
import com.furniture.core.model.Review;
import com.furniture.core.model.User;
import com.furniture.core.repository.RestaurantRepository;
import com.furniture.core.repository.ReviewRepository;
import com.furniture.core.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService {
  private ReviewRepository reviewRepository;
  private RestaurantRepository restaurantRepository;

  public ReviewServiceImplementation(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository) {
    this.reviewRepository = reviewRepository;
    this.restaurantRepository = restaurantRepository;
  }

  @Override
  public Review submitReview(ReviewRequest reviewRequest, User user) {
    Review review = new Review();
    System.out.println(reviewRequest);

    System.out.println(reviewRequest.getRestaurantId());
    Optional<Restaurant> restaurant = restaurantRepository.findById(reviewRequest.getRestaurantId());
    if (restaurant.isPresent()) {
      review.setRestaurant(restaurant.get());
    }
    review.setCustomer(user);
    review.setMessage(reviewRequest.getReviewText());
    review.setRating(reviewRequest.getRating());
    review.setCreatedAt(LocalDateTime.now());

    return reviewRepository.save(review);
  }

  @Override
  public void deleteReview(Long reviewId) throws ReviewException {
    Optional<Review> optionalReview = reviewRepository.findById(reviewId);

    if (optionalReview.isPresent()) {
      reviewRepository.deleteById(reviewId);
    } else {
      throw new ReviewException("Review with ID " + reviewId + " not found");
    }
  }

  @Override
  public double calculateAverageRating(List<Review> reviews) {
    double totalRating = 0;

    for (Review review : reviews) {
      totalRating += review.getRating();
    }

    if (reviews.size() > 0) {
      return totalRating / reviews.size();
    } else {
      return 0;
    }
  }
}
