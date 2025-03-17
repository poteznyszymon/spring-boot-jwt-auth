package com.example.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {
    @NotEmpty(message = "username is required")
    @Size(max = 30, message = "Max size of username is 30 characters")
    private String username;

    @NotEmpty(message = "firstname is required")
    private String firstName;

    @NotEmpty(message = "secondname is required")
    private String lastName;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotEmpty(message = "password is required")
    private String password;
}
