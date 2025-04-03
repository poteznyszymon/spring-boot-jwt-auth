package com.example.auth.dto.operatingHours;

import com.example.auth.dto.timeRange.TimeRangeDto;
import lombok.Data;

@Data
public class OperatingHoursDto {

    private long id;

    private TimeRangeDto monday;

    private TimeRangeDto tuesday;

    private TimeRangeDto wednesday;

    private TimeRangeDto thursday;

    private TimeRangeDto friday;

    private TimeRangeDto saturday;

    private TimeRangeDto sunday;
}
