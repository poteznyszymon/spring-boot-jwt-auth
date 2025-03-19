package com.example.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PostCreateDto {
    @NotEmpty(message = "content is required")
    public String content;
}
