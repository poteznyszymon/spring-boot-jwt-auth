package com.example.auth.dto.address;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AddressCreateDto {

    //@NotEmpty(message = "streetName name is required")
    private String streetName;
    //@NotEmpty(message = "streetNumber name is required")
    private String streetNumber;
    //@NotEmpty(message = "city name is required")
    private String city;
    //@NotEmpty(message = "state name is required")
    private String state;
    //@NotEmpty(message = "postalCode name is required")
    private String postalCode;
    //@NotEmpty(message = "country name is required")
    private String country;

}
