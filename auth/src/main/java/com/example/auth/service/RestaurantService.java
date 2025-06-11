package com.example.auth.service;

import com.example.auth.dto.DtoConverter;
import com.example.auth.dto.restaurant.RestaurantCreateDto;
import com.example.auth.dto.restaurant.RestaurantSummaryDto;
import com.example.auth.exception.ResourceNotFoundException;
import com.example.auth.model.*;
import com.example.auth.repository.RestaurantRepository;
import com.example.auth.repository.RestaurantRepositoryWithPagination;
import com.example.auth.repository.ReviewRepository;
import com.example.auth.repository.UserRepository;
import com.example.auth.util.CuisineType;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantRepositoryWithPagination restaurantRepositoryWithPagination;
    private final UserService userService;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    public RestaurantService(
            RestaurantRepository restaurantRepository,
            RestaurantRepositoryWithPagination restaurantRepositoryWithPagination,
            UserService userService,
            ReviewRepository reviewRepository,
            UserRepository userRepository, CloudinaryService cloudinaryService) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantRepositoryWithPagination = restaurantRepositoryWithPagination;
        this.userService = userService;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public Page<RestaurantSummaryDto> listRestaurants(Pageable pageable, CuisineType cuisineType) {
        if (cuisineType != null) {
            return restaurantRepositoryWithPagination.findAllByCuisineType(pageable, cuisineType).map(restaurantEntity -> DtoConverter.convertToDto(restaurantEntity, RestaurantSummaryDto.class));
        }
        return restaurantRepositoryWithPagination.findAll(pageable).map(restaurantEntity -> DtoConverter.convertToDto(restaurantEntity, RestaurantSummaryDto.class));
    }

    public RestaurantEntity getRestaurantById(long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with provided id"));
    }

    public List<RestaurantEntity> getRestaurantsContainingName(String name) {
        return restaurantRepository.findByNameContainingIgnoreCase(name);
    }

    public RestaurantEntity AddRestaurant(
            @AuthenticationPrincipal User user,
            RestaurantCreateDto restaurantCreateDto
    ) {

        UserEntity currentUser =  userService.findByUsername(user.getUsername());

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setName(restaurantCreateDto.getName());
        restaurant.setCuisineType(restaurantCreateDto.getCuisineType());
        restaurant.setPhoneNumber(restaurantCreateDto.getPhoneNumber());
        restaurant.setWebsiteUrl(restaurantCreateDto.getWebsiteUrl());
        restaurant.setAddress(DtoConverter.convertToEntity(restaurantCreateDto.getAddress(), AddressEntity.class));
        restaurant.setGeolocation(DtoConverter.convertToEntity(restaurantCreateDto.getGeolocation(), GeolocationEntity.class));
        restaurant.setOperatingHours(DtoConverter.convertToEntity(restaurantCreateDto.getOperatingHours(), OperatingHoursEntity.class));
        restaurant.setCreatedBy(currentUser);
        
        return restaurantRepository.save(restaurant);
    }

    public RestaurantEntity deleteRestaurant(
            @AuthenticationPrincipal User user,
            long restaurantId
    ) throws FileUploadException {
        UserEntity currentUser = userService.findByUsername(user.getUsername());
        RestaurantEntity restaurantToDelete = getRestaurantById(restaurantId);

        if (!restaurantToDelete.getCreatedBy().equals(currentUser)) {
            throw new AccessDeniedException("Can't delete restaurant, you are not creator");
        }

        for (ImageEntity image: restaurantToDelete.getImages()) {
            cloudinaryService.deleteImageByUrl(image.getUrl());
        }

        restaurantRepository.delete(restaurantToDelete);
        return restaurantToDelete;
    }

}
