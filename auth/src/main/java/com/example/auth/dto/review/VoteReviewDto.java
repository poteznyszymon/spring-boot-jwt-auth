package com.example.auth.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class VoteReviewDto {
    private ReviewDto review;
    private String action;
}
