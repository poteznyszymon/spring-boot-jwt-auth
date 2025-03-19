package com.example.auth.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "follows")
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "follow_id_seq")
    public long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "following_user_id", nullable = false)
    public UserEntity following_user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "followed_user_id", nullable = false)
    public UserEntity followed_user;


}
