package com.example.auth.repository;

import com.example.auth.model.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Optional<ReviewEntity> findById(long id);

    List<ReviewEntity> findTop3ByCreatedBy_UsernameOrderByCreatedAtDesc(String username);

    @Query("SELECT COALESCE(SUM(r.totalHelpfulVotes), 0) FROM ReviewEntity r WHERE r.createdBy.username = :username")
    Integer countHelpfulVotesByUser(@Param("username") String username);

    @Query("SELECT AVG(r.rating) FROM ReviewEntity r WHERE r.createdBy.username = :username")
    Float findAverageRatingByUsername(@Param("username") String username);

    Integer countByCreatedBy_Username(String username);
}
