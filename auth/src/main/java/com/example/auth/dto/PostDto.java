package com.example.auth.dto;

import com.example.auth.model.PostEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private long id;
    private String content;
    private LocalDateTime createdAt;
    private UserDto user;

    public static PostDto toDto(PostEntity post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUser(UserDto.toDto(post.getUser()));
        return dto;
    }
}
