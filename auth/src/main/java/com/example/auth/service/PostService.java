package com.example.auth.service;

import com.example.auth.dto.FollowDto;
import com.example.auth.dto.PostCreateDto;
import com.example.auth.dto.PostDto;
import com.example.auth.dto.UserDto;
import com.example.auth.exception.PostNotFoundException;
import com.example.auth.model.PostEntity;
import com.example.auth.model.UserEntity;
import com.example.auth.repository.PostRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final FollowService followService;

    public PostService(PostRepository postRepository, FollowService followService) {
        this.postRepository = postRepository;
        this.followService = followService;
    }

    public PostEntity createPost(PostCreateDto postDto, UserEntity user) {
        PostEntity newPost = new PostEntity();
        newPost.setContent(postDto.getContent());
        newPost.setUser(user);
        return postRepository.save(newPost);
    }

    public List<PostEntity> getUserPostsByUsername(String username) {
        Optional<List<PostEntity>> posts = postRepository.getPostEntitiesByUserUsername(username);
        return posts.orElse(Collections.emptyList());
    }

    public PostEntity deletePost(long postId, String currentUserUsername) {
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post with that id not found"));
        if (!Objects.equals(post.getUser().getUsername(), currentUserUsername)) {
            throw new AccessDeniedException("You do not have permission to delete this post");
        }
        postRepository.deleteById(postId);
        return post;
    }

    public PostEntity editPost(long postId, String currentUserUsername, String newContent) {
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post with that id not found"));
        if (!Objects.equals(post.getUser().getUsername(), currentUserUsername)) {
            throw new AccessDeniedException("You do not have permission to delete this post");
        }
        postRepository.updatePostContent(postId, newContent);
        post.setContent(newContent);
        return post;
    }

    public List<PostDto> getFollowingUsersPosts(String username) {
        List<UserDto> followingUsers = followService.getFollowedUsers(username);
        List<String> followingUsersUsernames = followingUsers.stream().map(UserDto::getUsername).collect(Collectors.toList());

        List<PostEntity> posts = postRepository.findFollowingUsersPosts(followingUsersUsernames);
        return posts.stream().map(PostDto::toDto).collect(Collectors.toList());
    }
}
