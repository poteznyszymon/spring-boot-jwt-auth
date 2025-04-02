package com.example.auth.service;

import com.example.auth.model.RestaurantEntity;
import com.example.auth.repository.RestaurantRepository;
import com.example.auth.repository.RestaurantRepositoryWithPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantRepositoryWithPagination restaurantRepositoryWithPagination;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantRepositoryWithPagination restaurantRepositoryWithPagination) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantRepositoryWithPagination = restaurantRepositoryWithPagination;
    }

    public Page<RestaurantEntity> listRestaurants(Pageable pageable) {
        return restaurantRepositoryWithPagination.findAll(pageable);
    }

    public String CreateRestaurant() {
        return "restaurant created";
    }

}
