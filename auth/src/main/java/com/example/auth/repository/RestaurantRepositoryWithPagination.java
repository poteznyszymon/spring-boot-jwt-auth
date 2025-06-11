package com.example.auth.repository;

import com.example.auth.model.RestaurantEntity;
import com.example.auth.util.CuisineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RestaurantRepositoryWithPagination extends PagingAndSortingRepository<RestaurantEntity, Long> {
    Page<RestaurantEntity> findAll(Pageable pageable);
    Page<RestaurantEntity> findAllByCuisineType(Pageable pageable, CuisineType cuisineType);
}
