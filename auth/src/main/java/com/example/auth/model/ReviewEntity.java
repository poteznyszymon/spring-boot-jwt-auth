package com.example.auth.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@Data
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_id_seq")
    private long id;

    @Column(nullable = false, length = 1024)
    private String content;

    @Column(nullable = false)
    private int rating;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime editedAt;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageEntity> images;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurant;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "review_helpful_votes",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> helpfulVoters = new HashSet<>();

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer totalHelpfulVotes = 0;
}
