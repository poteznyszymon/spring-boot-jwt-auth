package com.example.auth.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "time_range")
@NoArgsConstructor
@Data
public class TimeRangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    private long id;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;

    @OneToOne(mappedBy = "time_range")
    private OperatingHoursEntity operatingHours;

}
