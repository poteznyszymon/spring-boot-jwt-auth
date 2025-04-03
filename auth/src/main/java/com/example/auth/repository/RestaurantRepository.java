package com.example.auth.repository;

import com.example.auth.model.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    Optional<RestaurantEntity> findById(long id);
    List<RestaurantEntity> findByNameContainingIgnoreCase(String name);
}
