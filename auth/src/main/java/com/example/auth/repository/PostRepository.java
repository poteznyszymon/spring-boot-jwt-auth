package com.example.auth.repository;

import com.example.auth.model.PostEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<List<PostEntity>> getPostEntitiesByUserUsername(String username);
    void deleteById(@Nonnull Long postId);
    Optional<PostEntity> findById(@Nonnull Long id);

    @Modifying
    @Transactional
    @Query("UPDATE PostEntity p SET p.content = :newContent WHERE p.id = :id")
    void updatePostContent(Long id, String newContent);

    @Query("SELECT p FROM PostEntity p WHERE p.user.username IN :usernames")
    List<PostEntity> findFollowingUsersPosts(@Param("usernames") List<String> usernames);
}
