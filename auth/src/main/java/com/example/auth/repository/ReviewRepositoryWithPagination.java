package com.example.auth.repository;

import com.example.auth.model.ReviewEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReviewRepositoryWithPagination extends PagingAndSortingRepository<ReviewEntity, Long> {
    @Nonnull Page<ReviewEntity> findByCreatedBy_Username(@Nonnull Pageable pageable, String username);
}
