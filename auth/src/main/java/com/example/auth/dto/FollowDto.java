package com.example.auth.dto;

import com.example.auth.model.FollowEntity;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

@Data
public class FollowDto {
    private long id;
    private UserDto followingUser;
    private UserDto followedUser;

    public static FollowDto toDto(FollowEntity followEntity) {
        FollowDto dto = new FollowDto();
        dto.setId(followEntity.getId());
        dto.setFollowedUser(UserDto.toDto(followEntity.followed_user));
        dto.setFollowingUser(UserDto.toDto(followEntity.following_user));
        return dto;
    }
}
