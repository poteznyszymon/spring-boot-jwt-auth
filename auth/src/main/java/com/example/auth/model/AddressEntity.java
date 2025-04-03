package com.example.auth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@NoArgsConstructor
@Data
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_seq")
    private long id;

    @Column(nullable = false)
    private String streetName;

    @Column(nullable = false)
    private String streetNumber;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String country;

    @OneToOne(mappedBy = "address")
    @JsonBackReference
    private RestaurantEntity restaurant;
}
