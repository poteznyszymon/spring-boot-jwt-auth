package com.example.auth.dto;

import com.example.auth.model.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;

    public static UserResponseDto toDto(UserEntity user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
