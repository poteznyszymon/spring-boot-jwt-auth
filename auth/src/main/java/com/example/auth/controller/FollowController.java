package com.example.auth.controller;

import com.example.auth.dto.FollowDto;
import com.example.auth.dto.UserDto;
import com.example.auth.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/api/follow/{username}")
    public ResponseEntity<FollowDto> unfollowUser(@PathVariable String username,
                                                  @AuthenticationPrincipal User user)
    {
        FollowDto unfollow = followService.unfollowUser(username, user.getUsername());
        return ResponseEntity.ok(unfollow);
    }

    @GetMapping("/api/follow/followed")
    public ResponseEntity<List<UserDto>> getCurrentUserFollowedUsers(
            @RequestParam(required = false) String username,
            @AuthenticationPrincipal User user
    )
    {
        if (username == null) username = user.getUsername();

        List<UserDto> followingUsers = followService.getFollowedUsers(username);
        return ResponseEntity.ok(followingUsers);
    }

    @GetMapping("/api/follow/following")
    public ResponseEntity<List<UserDto>> getCurrentUserFollowingUsers(
            @RequestParam(required = false) String username,
            @AuthenticationPrincipal User user
    )
    {
        if (username == null) username = user.getUsername();
        List<UserDto> followingUsers = followService.getFollowingUsers(username);
        return ResponseEntity.ok(followingUsers);
    }
}
