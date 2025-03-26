package com.example.auth.repository;

import com.example.auth.model.FollowEntity;
import com.example.auth.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    @Query("SELECT f FROM FollowEntity f WHERE f.following_user.id = :followingUserId AND f.followed_user.id = :followedUserId")
    Optional<FollowEntity> findByFollowed_userIdAndFollowing_userId(long followedUserId, long followingUserId);

    @Modifying
    @Transactional
    @Query("delete from FollowEntity f WHERE f.followed_user.id = :followedUserId AND f.following_user.id = :followingUserId")
    void deleteByFollowed_userIdAndFollowing_userId(long followedUserId, long followingUserId);

    @Query("SELECT f.followed_user FROM FollowEntity f WHERE f.following_user.id = :followingUserId")
    List<UserEntity> findFollowedUsersByFollowingUserId(long followingUserId);

    @Query("SELECT f.following_user FROM FollowEntity f WHERE f.followed_user.id = :followedUserId")
    List<UserEntity> findFollowingUsersByFollowedUserId(long followedUserId);


}
