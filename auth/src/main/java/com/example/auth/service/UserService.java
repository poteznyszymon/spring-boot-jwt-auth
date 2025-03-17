package com.example.auth.service;

import com.example.auth.model.UserEntity;
import com.example.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with username " + username);
        }

        UserEntity userToReturn = user.get();
        userToReturn.setPassword("");

        return userToReturn;

    }
}
