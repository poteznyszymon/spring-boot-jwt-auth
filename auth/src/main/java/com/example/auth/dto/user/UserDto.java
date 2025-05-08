package com.example.auth.dto.user;

import com.example.auth.dto.image.ImageDto;
import com.example.auth.model.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String profileDescription;
    private ImageDto profileImage;
    private ImageDto coverImage;
    private LocalDateTime createdAt;
}
