package com.example.auth.service;

import com.example.auth.dto.FollowDto;
import com.example.auth.exception.FollowException;
import com.example.auth.model.FollowEntity;
import com.example.auth.model.UserEntity;
import com.example.auth.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class FollowService {

    final FollowRepository followRepository;
    final UserService userService;

    public FollowService(FollowRepository followRepository, UserService userService) {
        this.followRepository = followRepository;
        this.userService = userService;
    }

    public FollowDto followUser(String userToFollowUsername, String currentUserUsername) {
        if (Objects.equals(userToFollowUsername, currentUserUsername)) {
            throw new FollowException("You can't follow yourself");
        }

        UserEntity currentUser = userService.findByUsername(currentUserUsername);
        UserEntity userToFollow = userService.findByUsername(userToFollowUsername);

        Optional<FollowEntity> alreadyFollowing = followRepository.findByFollowed_userIdAndFollowing_userId(userToFollow.getId(),currentUser.getId());
        if (alreadyFollowing.isPresent()) {
            return FollowDto.toDto(alreadyFollowing.get());
        }

        FollowEntity follow = new FollowEntity();
        follow.setFollowing_user(currentUser);
        follow.setFollowed_user(userToFollow);
        followRepository.save(follow);
        return FollowDto.toDto(follow);
    }

}
