package com.example.auth.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CuisineTypeRequestParamConverter implements Converter<String, CuisineType> {

    @Override
    public CuisineType convert(String source) {
        try {
            return CuisineType.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid cuisine type: " + source);
        }
    }
}
