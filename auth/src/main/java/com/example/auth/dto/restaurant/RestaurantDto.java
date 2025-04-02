package com.example.auth.dto.restaurant;

import com.example.auth.util.CuisineType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RestaurantDto {

    private long id;

    private String name;

    private CuisineType cuisineType;

    private String phoneNumber;

    private String websiteUrl;



}
