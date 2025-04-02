package com.example.auth.dto.operatingHours;

import com.example.auth.dto.timeRange.TimeRangeCreateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OperatingHoursCreateDto {

    @Valid
    @NotNull(message = "Monday operating hours are required")
    private TimeRangeCreateDto monday;

    @Valid
    @NotNull(message = "Tuesday operating hours are required")
    private TimeRangeCreateDto tuesday;

    @Valid
    @NotNull(message = "Wednesday operating hours are required")
    private TimeRangeCreateDto wednesday;

    @Valid
    @NotNull(message = "Thursday operating hours are required")
    private TimeRangeCreateDto thursday;

    @Valid
    @NotNull(message = "Friday operating hours are required")
    private TimeRangeCreateDto friday;

    @Valid
    @NotNull(message = "Saturday operating hours are required")
    private TimeRangeCreateDto saturday;

    @Valid
    @NotNull(message = "Sunday operating hours are required")
    private TimeRangeCreateDto sunday;
}
