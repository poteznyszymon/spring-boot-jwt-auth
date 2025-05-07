package com.example.auth.controller;

import com.example.auth.dto.DtoConverter;
import com.example.auth.dto.image.CoverImageDto;
import com.example.auth.dto.image.ImageDto;
import com.example.auth.dto.image.ProfileImageDto;
import com.example.auth.dto.user.UserDetailsDto;
import com.example.auth.service.ImageService;
import com.example.auth.service.UserService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final ImageService imageService;
    private final UserService userService;

    public UserController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }


    @GetMapping("/{username}")
    public ResponseEntity<UserDetailsDto> getUserDetailsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserDetailsByUsername(username));
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
