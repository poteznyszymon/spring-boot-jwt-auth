package com.example.auth.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "operating_hours")
@NoArgsConstructor
@Data
public class OperatingHoursEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operating_hours_seq")
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "monday", referencedColumnName = "id")
    private TimeRangeEntity monday;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tuesday", referencedColumnName = "id")
    private TimeRangeEntity tuesday;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wednesday", referencedColumnName = "id")
    private TimeRangeEntity wednesday;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thursday", referencedColumnName = "id")
    private TimeRangeEntity thursday;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "friday", referencedColumnName = "id")
    private TimeRangeEntity friday;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "saturday", referencedColumnName = "id")
    private TimeRangeEntity saturday;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sunday", referencedColumnName = "id")
    private TimeRangeEntity sunday;
}
