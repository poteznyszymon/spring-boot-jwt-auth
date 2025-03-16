package com.example.auth.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
}
