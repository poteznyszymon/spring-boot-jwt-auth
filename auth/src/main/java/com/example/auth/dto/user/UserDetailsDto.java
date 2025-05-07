package com.example.auth.dto.user;

import com.example.auth.dto.image.ImageDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDetailsDto {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private ImageDto profileImage;
    private ImageDto coverImage;
    private int totalReviews = 0;
    private int totalFavorites = 0;
    private float averageRating;
    private String profileDescription;
    private LocalDateTime createdAt;
}
