package com.example.auth.service;

import com.example.auth.dto.DtoConverter;
import com.example.auth.dto.review.ReviewCreateDto;
import com.example.auth.dto.review.ReviewDto;
import com.example.auth.exception.ResourceNotFoundException;
import com.example.auth.model.ImageEntity;
import com.example.auth.model.RestaurantEntity;
import com.example.auth.model.ReviewEntity;
import com.example.auth.model.UserEntity;
import com.example.auth.repository.RestaurantRepository;
import com.example.auth.repository.RestaurantRepositoryWithPagination;
import com.example.auth.repository.ReviewRepository;
import com.example.auth.repository.ReviewRepositoryWithPagination;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final CloudinaryService cloudinaryService;
    private final RestaurantRepositoryWithPagination restaurantRepositoryWithPagination;
    private final ReviewRepositoryWithPagination reviewRepositoryWithPagination;

    public ReviewService(
            UserService userService,
            RestaurantService restaurantService,
            ReviewRepository reviewRepository,
            RestaurantRepository restaurantRepository,
            CloudinaryService cloudinaryService,
            RestaurantRepositoryWithPagination restaurantRepositoryWithPagination,
            ReviewRepositoryWithPagination reviewRepositoryWithPagination) {
        this.userService =  userService;
        this.restaurantService = restaurantService;
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.cloudinaryService = cloudinaryService;
        this.restaurantRepositoryWithPagination = restaurantRepositoryWithPagination;
        this.reviewRepositoryWithPagination = reviewRepositoryWithPagination;
    }

    public ReviewEntity getReviewById(long reviewId) {
        return reviewRepository
                .findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with provided id"));
    }

    public Page<ReviewDto> getReviewsByUserUsername(Pageable pageable, String username) {
        return reviewRepositoryWithPagination.
                findByCreatedBy_Username(pageable, username)
                .map(entity -> DtoConverter.convertToDto(entity, ReviewDto.class));
    }

    public List<ReviewDto> getRecentReviewsByUsername(String username) {
        return reviewRepository
                .findTop2ByCreatedBy_UsernameOrderByCreatedBy(username)
                .stream()
                .map(entity -> DtoConverter.convertToDto(entity, ReviewDto.class))
                .toList();
    }

    public Float getAverageRatingByUsername(String username) {
        return reviewRepository.findAverageRatingByUsername(username);
    }

    @Transactional
    public ReviewEntity addReviewToRestaurant(
            @AuthenticationPrincipal User user,
            long restaurantId,
            ReviewCreateDto reviewCreateDto
    ) {
        UserEntity currentUser = userService.findByUsername(user.getUsername());
        RestaurantEntity restaurant = restaurantService.getRestaurantById(restaurantId);

        restaurant.setTotalReviews(restaurant.getTotalReviews() + 1);
        int newTotal = restaurant.getTotalReviews();
        float newAverage = (restaurant.getAverageRatings() * (newTotal - 1) + reviewCreateDto.getRating()) / newTotal;
        restaurant.setAverageRatings(newAverage);
        restaurantRepository.save(restaurant);

        ReviewEntity review = new ReviewEntity();
        review.setContent(reviewCreateDto.getContent());
        review.setRating(reviewCreateDto.getRating());
        review.setCreatedBy(currentUser);
        review.setRestaurant(restaurant);

        return reviewRepository.save(review);
    }

    @Transactional
    public ReviewEntity deleteReviewById(
            @AuthenticationPrincipal User user,
            long reviewId
    ) throws FileUploadException {
        UserEntity currentUser = userService.findByUsername(user.getUsername());
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        if (!review.getCreatedBy().equals(currentUser)) {
            throw new AccessDeniedException("Can't delete review, you are not owner");
        }


        RestaurantEntity restaurant = review.getRestaurant();

        if (restaurant.getTotalReviews() > 1) {
            float newAverage = (restaurant.getAverageRatings() * restaurant.getTotalReviews() - review.getRating()) / (restaurant.getTotalReviews() - 1);
            restaurant.setAverageRatings(newAverage);
        }
        else {
            restaurant.setAverageRatings(0.f);
        }
        restaurant.setTotalReviews(restaurant.getTotalReviews() - 1);

        for (ImageEntity image: review.getImages()) {
            cloudinaryService.deleteImageByUrl(image.getUrl());
        }

        reviewRepository.delete(review);
        restaurantRepository.save(restaurant);
        return review;
    }

}
