package com.example.auth.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "geolocation")
public class GeolocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geolocation_id_seq")
    private long id;

    @Column(nullable = false)
    private float latitude;

    @Column(nullable = false)
    private float longitude;
}
