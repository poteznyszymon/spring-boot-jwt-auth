package com.example.auth.service;

import com.example.auth.controller.ReviewController;
import com.example.auth.dto.review.ReviewCreateDto;
import com.example.auth.exception.ResourceNotFoundException;
import com.example.auth.model.RestaurantEntity;
import com.example.auth.model.ReviewEntity;
import com.example.auth.model.UserEntity;
import com.example.auth.repository.ReviewRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final ReviewRepository reviewRepository;

    public ReviewService(
            UserService userService,
            RestaurantService restaurantService,
            ReviewRepository reviewRepository
    ) {
        this.userService =  userService;
        this.restaurantService = restaurantService;
        this.reviewRepository = reviewRepository;
    }

    public ReviewEntity addReviewToRestaurant(
            @AuthenticationPrincipal User user,
            long restaurantId,
            ReviewCreateDto reviewCreateDto
    ) {
        UserEntity currentUser = userService.findByUsername(user.getUsername());
        RestaurantEntity restaurant = restaurantService.getRestaurantById(restaurantId);

        ReviewEntity review = new ReviewEntity();
        review.setContent(reviewCreateDto.getContent());
        review.setRating(reviewCreateDto.getRating());
        review.setCreatedBy(currentUser);
        review.setRestaurant(restaurant);

        return reviewRepository.save(review);
    }

    public ReviewEntity deleteReviewFromRestaurant() {
        return null;
    }

}
