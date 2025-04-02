package com.example.auth.repository;

import com.example.auth.model.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RestaurantRepositoryWithPagination extends PagingAndSortingRepository<RestaurantEntity, Long> {
    Page<RestaurantEntity> findAll(Pageable pageable);
}
