package com.example.auth.controller;

import com.example.auth.dto.image.CoverImageDto;
import com.example.auth.dto.image.ImageDto;
import com.example.auth.dto.image.ProfileImageDto;
import com.example.auth.service.ImageService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final ImageService imageService;

    public UserController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/profile-image")
    public ResponseEntity<ImageDto> changeProfileImage(
            @AuthenticationPrincipal User user,
            @ModelAttribute ProfileImageDto profileImageDto
            ) throws FileUploadException {
        return ResponseEntity.ok(imageService.uploadProfileImageToUser(user, profileImageDto));
    }

    @PostMapping("/cover-image")
    public ResponseEntity<ImageDto> changeCoverImage(
            @AuthenticationPrincipal User user,
            @ModelAttribute CoverImageDto coverImageDto
            ) throws FileUploadException {
        return ResponseEntity.ok(imageService.uploadCoverImageToUser(user, coverImageDto));
    }
}
