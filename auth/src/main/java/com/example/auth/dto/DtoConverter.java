package com.example.auth.dto;

import com.example.auth.dto.auth.OperatingHoursDto;
import com.example.auth.dto.timeRange.TimeRangeCreateDto;
import com.example.auth.model.OperatingHoursEntity;
import com.example.auth.model.TimeRangeEntity;
import org.modelmapper.ModelMapper;

public class DtoConverter {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static OperatingHoursDto convertToDto(OperatingHoursEntity entity) {
        return modelMapper.map(entity, OperatingHoursDto.class);
    }

    public static TimeRangeCreateDto convertToDto(TimeRangeEntity entity) {
        return modelMapper.map(entity, TimeRangeCreateDto.class);
    }
}
