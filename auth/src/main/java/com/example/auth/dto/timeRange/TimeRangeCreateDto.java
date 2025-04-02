package com.example.auth.dto.timeRange;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class TimeRangeCreateDto {

    @NotNull(message = "openTime is required")
    private LocalTime openTime;

    @NotNull(message = "openTime is required")
    private LocalTime closeTime;

}
