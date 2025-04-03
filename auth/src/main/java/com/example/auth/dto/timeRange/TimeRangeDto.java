package com.example.auth.dto.timeRange;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TimeRangeDto {

    private long id;

    private LocalTime openTime;

    private LocalTime closeTime;
}
