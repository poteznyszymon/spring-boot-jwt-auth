package com.example.auth.dto;

import com.example.auth.model.ReviewEntity;
import org.modelmapper.ModelMapper;

public class DtoConverter {
    private static final ModelMapper modelMapper = new ModelMapper();

    /// Entity -----> Dto
    public static <D, E> D convertToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    /// Dto ----> Entity
    public static <E, D> E convertToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }


}
