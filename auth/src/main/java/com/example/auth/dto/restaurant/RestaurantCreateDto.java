package com.example.auth.dto.restaurant;

import com.example.auth.dto.address.AddressCreateDto;
import com.example.auth.dto.geolocation.GeolocationCreateDto;
import com.example.auth.dto.operatingHours.OperatingHoursCreateDto;
import com.example.auth.util.CuisineType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RestaurantCreateDto {

    @NotEmpty(message = "restaurant name is required")
    private String name;

    @NotNull(message = "cuisineType is required")
    private CuisineType cuisineType;

    private String phoneNumber;

    private String websiteUrl;

    private AddressCreateDto address;

    private GeolocationCreateDto geolocation;

    @Valid
    private OperatingHoursCreateDto operatingHours;

}
