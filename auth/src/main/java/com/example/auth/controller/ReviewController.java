package com.example.auth.controller;

import com.example.auth.dto.DtoConverter;
import com.example.auth.dto.review.ReviewCreateDto;
import com.example.auth.dto.review.ReviewDto;
import com.example.auth.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{restaurantId}")
    public ResponseEntity<ReviewDto> addReviewToRestaurant(
            @PathVariable long restaurantId,
            @Valid @RequestBody ReviewCreateDto reviewCreateDto,
            @AuthenticationPrincipal User user
    )
    {
        return ResponseEntity.ok(DtoConverter
                .convertToDto(reviewService.addReviewToRestaurant(user, restaurantId, reviewCreateDto), ReviewDto.class)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDto> deleteReviewById(
            @PathVariable long id,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(DtoConverter
                .convertToDto(reviewService.deleteReviewById(user, id), ReviewDto.class)
        );
    }

}
