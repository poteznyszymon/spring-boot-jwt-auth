package com.example.auth.service;

import com.example.auth.dto.image.ImageCreateDto;
import com.example.auth.model.ImageEntity;
import com.example.auth.model.RestaurantEntity;
import com.example.auth.model.ReviewEntity;
import com.example.auth.model.UserEntity;
import com.example.auth.repository.ImageRepository;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final RestaurantService restaurantService;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;
    private final ReviewService reviewService;

    public ImageService(
            ImageRepository imageRepository,
            RestaurantService restaurantService,
            CloudinaryService cloudinaryService,
            UserService userService, ReviewService reviewService) {
        this.imageRepository = imageRepository;
        this.restaurantService = restaurantService;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    public ImageEntity uploadNewImageToRestaurant(
            long restaurantId,
            ImageCreateDto imageCreateDto
    ) throws FileUploadException {

        if (imageCreateDto.getFile().isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        RestaurantEntity restaurant = restaurantService.getRestaurantById(restaurantId);

        ImageEntity image = new ImageEntity();
        image.setRestaurant(restaurant);
        image.setUrl(cloudinaryService.uploadFile(imageCreateDto.getFile()));

        return imageRepository.save(image);
    }

    public ImageEntity uploadNewImageToReview(
            @AuthenticationPrincipal User user,
            long reviewId,
            ImageCreateDto imageCreateDto
    ) throws FileUploadException {
        UserEntity currentUser = userService.findByUsername(user.getUsername());
        ReviewEntity review = reviewService.getReviewById(reviewId);

        if (!review.getCreatedBy().equals(currentUser)) {
            throw new AccessDeniedException("Can't add image to review. You are not the owner");
        }

        ImageEntity image = new ImageEntity();
        image.setReview(review);
        image.setUrl(cloudinaryService.uploadFile(imageCreateDto.getFile()));

        return imageRepository.save(image);
    }


}
