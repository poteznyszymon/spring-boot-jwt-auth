package com.example.auth.dto.restaurant;

import com.example.auth.dto.address.AddressDto;
import com.example.auth.dto.geolocation.GeolocationDto;
import com.example.auth.dto.image.ImageDto;
import com.example.auth.dto.operatingHours.OperatingHoursDto;
import com.example.auth.util.CuisineType;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantSummaryDto {

    private long id;

    private String name;

    private CuisineType cuisineType;

    private String phoneNumber;

    private String websiteUrl;

    private float averageRatings;

    private float totalReviews;

    private AddressDto address;

    private List<ImageDto> images;

    private GeolocationDto geolocation;

}
