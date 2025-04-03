package com.example.auth.service;

import com.example.auth.dto.DtoConverter;
import com.example.auth.dto.restaurant.RestaurantCreateDto;
import com.example.auth.dto.review.ReviewCreateDto;
import com.example.auth.exception.ResourceNotFoundException;
import com.example.auth.model.*;
import com.example.auth.repository.RestaurantRepository;
import com.example.auth.repository.RestaurantRepositoryWithPagination;
import com.example.auth.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public RestaurantService(
            RestaurantRepository restaurantRepository,
            RestaurantRepositoryWithPagination restaurantRepositoryWithPagination,
            UserService userService,
            ReviewRepository reviewRepository
            ) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantRepositoryWithPagination = restaurantRepositoryWithPagination;
        this.userService = userService;
        this.reviewRepository = reviewRepository;
    }

    public Page<RestaurantEntity> listRestaurants(Pageable pageable) {
        return restaurantRepositoryWithPagination.findAll(pageable);
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

}
