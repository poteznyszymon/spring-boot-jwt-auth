package com.example.auth.dto.user;

import com.example.auth.model.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;

    public static UserDto toDto(UserEntity user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
