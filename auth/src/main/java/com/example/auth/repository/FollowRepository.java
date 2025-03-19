package com.example.auth.repository;

import com.example.auth.model.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    @Query("SELECT f FROM FollowEntity f WHERE f.following_user.id = :FollowingUserId AND f.followed_user.id = :followedUserId")
    Optional<FollowEntity> findByFollowed_userIdAndFollowing_userId(long followedUserId, long FollowingUserId);
}
