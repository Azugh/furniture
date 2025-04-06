package com.furniture.api.controller;

import com.furniture.api.service.ReviewService;
import com.furniture.api.service.UserService;
import com.furniture.core.exception.ReviewException;
import com.furniture.core.exception.UserException;
import com.furniture.core.model.Review;
import com.furniture.core.model.User;
import com.furniture.core.request.ReviewRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private ReviewService reviewService;
    private UserService userService;

    public ReviewController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @PostMapping("/review")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest review, @RequestHeader("Authorization") String jwt) throws UserException {
        User user =userService.findUserProfileByJwt(jwt);
        Review submitedReview = reviewService.submitReview(review,user);
        return ResponseEntity.ok(submitedReview);
    }



    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) throws ReviewException {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/average-rating")
    public ResponseEntity<Double> calculateAverageRating(@RequestBody List<Review> reviews) {
        double averageRating = reviewService.calculateAverageRating(reviews);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }
}