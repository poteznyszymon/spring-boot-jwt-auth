package com.example.auth.dto.address;

import lombok.Data;

@Data
public class AddressDto {

    private long id;
    private String streetName;
    private String streetNumber;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
