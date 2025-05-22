package com.example.auth.service;

import com.example.auth.dto.DtoConverter;
import com.example.auth.dto.user.UserDetailsDto;
import com.example.auth.dto.user.UserDto;
import com.example.auth.dto.user.UserEditDataDto;
import com.example.auth.exception.ResourceNotFoundException;
import com.example.auth.model.UserEntity;
import com.example.auth.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RatingService ratingService;

    public UserService(UserRepository userRepository, RatingService ratingService) {
        this.userRepository = userRepository;
        this.ratingService = ratingService;
    }

    public UserEntity findByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with username " + username);
        }

        return user.get();
    }

    public UserDetailsDto getUserDetailsByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found with username " + username);
        }
    
        UserDetailsDto userDto = DtoConverter.convertToDto(user.get(), UserDetailsDto.class);
        userDto.setAverageRating(ratingService.getAverageRatingByUsername(username));
        userDto.setTotalReviews(ratingService.getTotalReviewsByUsername(username));

        return userDto;
    }

    public UserDto editUserData(
            @AuthenticationPrincipal User user,
            UserEditDataDto editUserData
    ) {
        UserEntity currentUser = findByUsername(user.getUsername());

        String newDescription = editUserData.getProfileDescription();

        if (newDescription != null && !newDescription.isEmpty() && !newDescription.equals(currentUser.getProfileDescription())) {
            currentUser.setProfileDescription(newDescription);
        }

        return DtoConverter.convertToDto(userRepository.save(currentUser), UserDto.class);
    }

    public UserEntity findById(long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with id " + userId);
        }

        return user.get();
    }
}
