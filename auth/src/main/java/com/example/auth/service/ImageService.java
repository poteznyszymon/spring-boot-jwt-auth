package com.example.auth.service;

import com.example.auth.dto.DtoConverter;
import com.example.auth.dto.image.CoverImageDto;
import com.example.auth.dto.image.ImageCreateDto;
import com.example.auth.dto.image.ImageDto;
import com.example.auth.dto.image.ProfileImageDto;
import com.example.auth.model.ImageEntity;
import com.example.auth.model.RestaurantEntity;
import com.example.auth.model.ReviewEntity;
import com.example.auth.model.UserEntity;
import com.example.auth.repository.ImageRepository;
import com.example.auth.repository.UserRepository;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final RestaurantService restaurantService;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final UserRepository userRepository;

    public ImageService(
            ImageRepository imageRepository,
            RestaurantService restaurantService,
            CloudinaryService cloudinaryService,
            UserService userService,
            UserRepository userRepository,
            ReviewService reviewService
    ) {
        this.imageRepository = imageRepository;
        this.restaurantService = restaurantService;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.userRepository = userRepository;
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

    @Transactional
    public ImageDto uploadProfileImageToUser(
            @AuthenticationPrincipal User user,
            ProfileImageDto profileImageDto
    ) throws FileUploadException {
        UserEntity currentUser = userService.findByUsername(user.getUsername());

        ImageEntity oldImage = currentUser.getProfileImage();
        if (oldImage != null && oldImage.getUrl() != null && !oldImage.getUrl().isEmpty()) {
            cloudinaryService.deleteImageByUrl(oldImage.getUrl());
            imageRepository.delete(oldImage);
        }

        ImageEntity newProfileImage = new ImageEntity();
        newProfileImage.setUrl(cloudinaryService.uploadFile(profileImageDto.getFile()));
        imageRepository.save(newProfileImage);

        currentUser.setProfileImage(newProfileImage);
        userRepository.save(currentUser);

        return DtoConverter.convertToDto(newProfileImage, ImageDto.class);
    }

    @Transactional
    public ImageDto uploadCoverImageToUser(
            @AuthenticationPrincipal User user,
            CoverImageDto coverImageDto
    ) throws FileUploadException {
        UserEntity currentUser = userService.findByUsername(user.getUsername());

        ImageEntity oldImage = currentUser.getCoverImage();
        if (oldImage != null && oldImage.getUrl() != null && !oldImage.getUrl().isEmpty()) {

            currentUser.setCoverImage(null);
            userRepository.save(currentUser);

            cloudinaryService.deleteImageByUrl(oldImage.getUrl());
            imageRepository.delete(oldImage);
        }

        ImageEntity newCoverImage = new ImageEntity();
        newCoverImage.setUrl(cloudinaryService.uploadFile(coverImageDto.getFile()));
        imageRepository.save(newCoverImage);

        currentUser.setCoverImage(newCoverImage);
        userRepository.save(currentUser);

        return DtoConverter.convertToDto(newCoverImage, ImageDto.class);
    }

}
