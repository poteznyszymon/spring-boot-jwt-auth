package com.example.auth.dto.review;

import com.example.auth.dto.image.ImageDto;
import com.example.auth.dto.restaurant.RestaurantSummaryDto;
import com.example.auth.dto.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class ReviewDto {

    private long id;
    private String content;
    private int rating;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private List<ImageDto> images;
    private UserDto createdBy;
    private RestaurantSummaryDto restaurant;
    ///private Set<UserDto> helpfulVoters;
    private int totalHelpfulVotes;
    private boolean votedHelpfulByCurrentUser;

}
