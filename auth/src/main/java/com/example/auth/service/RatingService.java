package com.example.auth.service;

import com.example.auth.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    private final ReviewRepository reviewRepository;

    public RatingService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Float getAverageRatingByUsername(String username) {
        return reviewRepository.findAverageRatingByUsername(username);
    }

    public Integer getTotalReviewsByUsername(String username) {
        return reviewRepository.countByCreatedBy_Username(username);
    }
}
