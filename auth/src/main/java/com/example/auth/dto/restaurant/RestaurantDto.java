package com.example.auth.dto.restaurant;

import com.example.auth.dto.address.AddressDto;
import com.example.auth.dto.geolocation.GeolocationDto;
import com.example.auth.dto.operatingHours.OperatingHoursDto;
import com.example.auth.util.CuisineType;
import lombok.Data;

@Data
public class RestaurantDto {

    private long id;

    private String name;

    private CuisineType cuisineType;

    private String phoneNumber;

    private String websiteUrl;

    private float averageRatings;

    private AddressDto address;

    private GeolocationDto geolocation;

    private OperatingHoursDto operatingHours;

}
