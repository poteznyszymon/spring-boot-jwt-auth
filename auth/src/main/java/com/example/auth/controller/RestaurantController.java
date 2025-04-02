package com.example.auth.controller;

import com.example.auth.dto.restaurant.RestaurantCreateDto;
import com.example.auth.model.RestaurantEntity;
import com.example.auth.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public Page<RestaurantEntity> listRestaurants(Pageable pageable) {
        return restaurantService.listRestaurants(pageable);
    }

    @PostMapping
    public ResponseEntity<RestaurantCreateDto> createRestaurant(
            @Valid @RequestBody RestaurantCreateDto restaurantCreateDto,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(restaurantCreateDto);
    }

}
