package com.example.auth.controller;

import com.example.auth.dto.FollowDto;
import com.example.auth.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/api/follow/{username}")
    public ResponseEntity<FollowDto> followUser(@PathVariable String username,
                                                @AuthenticationPrincipal User user
    ) {
        FollowDto follow = followService.followUser(username, user.getUsername());
        return ResponseEntity.ok(follow);
    }

}
