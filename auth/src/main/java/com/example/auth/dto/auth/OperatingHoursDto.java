package com.example.auth.dto.auth;

import com.example.auth.dto.timeRange.TimeRangeCreateDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OperatingHoursDto {

    private long id;

    @NotEmpty(message = "monday operating hours are required")
    private TimeRangeCreateDto monday;

    @NotEmpty(message = "tuesday operating hours are required")
    private TimeRangeCreateDto tuesday;

    @NotEmpty(message = "wednesday operating hours are required")
    private TimeRangeCreateDto wednesday;

    @NotEmpty(message = "thursday operating hours are required")
    private TimeRangeCreateDto thursday;

    @NotEmpty(message = "friday operating hours are required")
    private TimeRangeCreateDto friday;

    @NotEmpty(message = "saturday operating hours are required")
    private TimeRangeCreateDto saturday;

    @NotEmpty(message = "sunday operating hours are required")
    private TimeRangeCreateDto sunday;

}
