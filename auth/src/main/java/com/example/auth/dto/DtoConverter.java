package com.example.auth.dto;

import com.example.auth.dto.address.AddressCreateDto;
import com.example.auth.dto.auth.OperatingHoursDto;
import com.example.auth.dto.timeRange.TimeRangeCreateDto;
import com.example.auth.model.AddressEntity;
import com.example.auth.model.OperatingHoursEntity;
import com.example.auth.model.TimeRangeEntity;
import org.modelmapper.ModelMapper;

public class DtoConverter {
    private static final ModelMapper modelMapper = new ModelMapper();


    /// Entity -----> Dto

    public static <D, E> D convertToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
    /*
    public static OperatingHoursDto convertToDto(OperatingHoursEntity entity) {
        return modelMapper.map(entity, OperatingHoursDto.class);
    }

    public static TimeRangeCreateDto convertToDto(TimeRangeEntity entity) {
        return modelMapper.map(entity, TimeRangeCreateDto.class);
    }
    */


    ///  ----> Entity
    /*
    public static AddressEntity convertToEntity(AddressCreateDto dto) {
        return modelMapper.map(dto, AddressEntity.class);
    }

     */

    public static <E, D> E convertToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

}
