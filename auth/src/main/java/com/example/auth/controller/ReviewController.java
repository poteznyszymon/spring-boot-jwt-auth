package com.example.auth.controller;

import com.example.auth.dto.DtoConverter;
import com.example.auth.dto.image.ImageCreateDto;
import com.example.auth.dto.image.ImageDto;
import com.example.auth.dto.review.ReviewCreateDto;
import com.example.auth.dto.review.ReviewDto;
import com.example.auth.service.ImageService;
import com.example.auth.service.ReviewService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final ImageService imageService;

    public ReviewController(ReviewService reviewService, ImageService imageService) {
        this.reviewService = reviewService;
        this.imageService = imageService;
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
    ) throws FileUploadException {
        return ResponseEntity.ok(DtoConverter
                .convertToDto(reviewService.deleteReviewById(user, id), ReviewDto.class)
        );
    }

    @PostMapping("/{reviewId}/image")
    public ResponseEntity<ImageDto> addImageToReview(
            @AuthenticationPrincipal User user,
            @PathVariable long reviewId,
            @ModelAttribute ImageCreateDto imageCreateDto
    ) throws FileUploadException {
        return ResponseEntity.ok(
                DtoConverter.convertToDto(imageService.uploadNewImageToReview(user, reviewId, imageCreateDto), ImageDto.class));
    }

}
