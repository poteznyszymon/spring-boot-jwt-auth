package com.example.auth.controller;

import com.example.auth.dto.DtoConverter;
import com.example.auth.dto.image.ImageCreateDto;
import com.example.auth.dto.image.ImageDto;
import com.example.auth.dto.restaurant.RestaurantCreateDto;
import com.example.auth.dto.restaurant.RestaurantDetailsDto;
import com.example.auth.dto.restaurant.RestaurantSummaryDto;
import com.example.auth.model.RestaurantEntity;
import com.example.auth.service.ImageService;
import com.example.auth.service.RestaurantService;
import com.example.auth.util.CuisineType;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<RestaurantSummaryDto> listRestaurants(
            Pageable pageable,
            @RequestParam(required = false) CuisineType cuisine
    ) {
        return restaurantService.listRestaurants(pageable, cuisine);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RestaurantDetailsDto> getRestaurantById(@PathVariable long id) {
        return ResponseEntity.ok(
                DtoConverter.convertToDto(restaurantService.getRestaurantById(id), RestaurantDetailsDto.class)
        );
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<RestaurantSummaryDto>> get(@PathVariable String name) {
        List<RestaurantEntity> restaurants = restaurantService.getRestaurantsContainingName(name);
        return ResponseEntity.ok(restaurants.stream().map(
                entity -> DtoConverter.convertToDto(entity, RestaurantSummaryDto.class)
        ).collect(Collectors.toList()));
    }

    @GetMapping("/cuisines")
    public ResponseEntity<CuisineType[]> getAllCuisinesTypes() {
        return ResponseEntity.ok(CuisineType.values());
    }

    @PostMapping
    public ResponseEntity<RestaurantDetailsDto> AddRestaurant(
            @Valid @RequestBody RestaurantCreateDto restaurantCreateDto,
            @AuthenticationPrincipal User user
    ) {
        RestaurantDetailsDto restaurantDto = DtoConverter
                .convertToDto(restaurantService.AddRestaurant(user, restaurantCreateDto), RestaurantDetailsDto.class);
        return ResponseEntity.ok(restaurantDto);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDetailsDto> deleteRestaurant(
            @AuthenticationPrincipal User user,
            @PathVariable long restaurantId
    ) throws FileUploadException {
        return ResponseEntity.ok(DtoConverter.convertToDto(restaurantService.deleteRestaurant(user, restaurantId), RestaurantDetailsDto.class));
    }

    @PostMapping("/{restaurantId}/image")
    public ResponseEntity<ImageDto> addImageToRestaurant(
            @PathVariable long restaurantId,
            @ModelAttribute ImageCreateDto imageCreateDto
    ) throws FileUploadException {
        return ResponseEntity.ok(DtoConverter.convertToDto(imageService.uploadNewImageToRestaurant(restaurantId, imageCreateDto), ImageDto.class));
    }

}
