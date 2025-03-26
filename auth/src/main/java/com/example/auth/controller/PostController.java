package com.example.auth.controller;

import com.example.auth.dto.PostCreateDto;
import com.example.auth.dto.PostDto;
import com.example.auth.model.PostEntity;
import com.example.auth.model.UserEntity;
import com.example.auth.service.PostService;
import com.example.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateDto postDto,
                                              @AuthenticationPrincipal User user
    ) {
        UserEntity currentUser = userService.findByUsername(user.getUsername());
        PostEntity newPost = postService.createPost(postDto, currentUser);
        return ResponseEntity.ok(PostDto.toDto(newPost));
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<PostDto> deletePost(@PathVariable String postId,
                                              @AuthenticationPrincipal User user
    ) {
        long parsedPostId = Long.parseLong(postId);
        PostEntity deletedPost = postService.deletePost(parsedPostId, user.getUsername());
        return ResponseEntity.ok(PostDto.toDto(deletedPost));
    }

    @PutMapping("{postId}")
    public ResponseEntity<PostDto> editPost(@PathVariable String postId,
                                            @AuthenticationPrincipal User user,
                                            @RequestBody PostCreateDto postEditDto
    ) {
        long parsedPostId = Long.parseLong(postId);
        PostEntity editedPost = postService.editPost(parsedPostId, user.getUsername(), postEditDto.getContent());
        return ResponseEntity.ok(PostDto.toDto(editedPost));
    }

    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getCurrentUserPosts(@AuthenticationPrincipal User user) {
        List<PostEntity> posts = postService.getUserPostsByUsername(user.getUsername());
        return ResponseEntity.ok(posts.stream().map(PostDto::toDto).collect(Collectors.toList()));
    }

    @GetMapping("{username}")
    public ResponseEntity<List<PostDto>> getUserPostsByUsername(@PathVariable String username) {
        List<PostEntity> posts = postService.getUserPostsByUsername(username);
        return ResponseEntity.ok(posts.stream().map(PostDto::toDto).collect(Collectors.toList()));
    }

    @GetMapping("/following")
    public ResponseEntity<List<PostDto>> getFollowingUsersPosts(@AuthenticationPrincipal User user) {
        List<PostDto> posts = postService.getFollowingUsersPosts(user.getUsername());
        return ResponseEntity.ok(posts);
    }

}
