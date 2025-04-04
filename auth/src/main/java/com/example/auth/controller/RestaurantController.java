package com.example.auth.controller;

import com.example.auth.dto.DtoConverter;
import com.example.auth.dto.image.ImageCreateDto;
import com.example.auth.dto.image.ImageDto;
import com.example.auth.dto.restaurant.RestaurantCreateDto;
import com.example.auth.dto.restaurant.RestaurantDto;
import com.example.auth.dto.review.ReviewCreateDto;
import com.example.auth.dto.review.ReviewDto;
import com.example.auth.model.RestaurantEntity;
import com.example.auth.service.ImageService;
import com.example.auth.service.RestaurantService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final ImageService imageService;

    public RestaurantController(
            RestaurantService restaurantService,
            ImageService imageService
    ) {
        this.restaurantService = restaurantService;
        this.imageService = imageService;
    }

    @GetMapping
    public Page<RestaurantDto> listRestaurants(Pageable pageable) {
        return restaurantService.listRestaurants(pageable)
                .map(entity -> DtoConverter.convertToDto(entity, RestaurantDto.class));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable long id) {
        return ResponseEntity.ok(
                DtoConverter.convertToDto(restaurantService.getRestaurantById(id), RestaurantDto.class)
        );
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<RestaurantDto>> get(@PathVariable String name) {
        List<RestaurantEntity> restaurants = restaurantService.getRestaurantsContainingName(name);
        return ResponseEntity.ok(restaurants.stream().map(
                entity -> DtoConverter.convertToDto(entity, RestaurantDto.class)
        ).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<RestaurantDto> AddRestaurant(
            @Valid @RequestBody RestaurantCreateDto restaurantCreateDto,
            @AuthenticationPrincipal User user
    ) {
        RestaurantDto restaurantDto = DtoConverter
                .convertToDto(restaurantService.AddRestaurant(user, restaurantCreateDto), RestaurantDto.class);
        return ResponseEntity.ok(restaurantDto);
    }

    @PostMapping("/{restaurantId}/image")
    public ResponseEntity<ImageDto> addImageToRestaurant(
            @PathVariable long restaurantId,
            @ModelAttribute ImageCreateDto imageCreateDto
    ) throws FileUploadException {
        return ResponseEntity.ok(DtoConverter.convertToDto(imageService.uploadNewImageToRestaurant(restaurantId, imageCreateDto), ImageDto.class));
    }

}
