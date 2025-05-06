package com.example.auth.dto.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileImageDto {
    MultipartFile file;
}
