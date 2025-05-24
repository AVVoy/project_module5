package com.example.project_module5.mapper;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.time.LocalDate;

public class LocalDateConverter implements Converter<LocalDate, LocalDate> {
    @Override
    public LocalDate convert(MappingContext<LocalDate, LocalDate> mappingContext) {
        return mappingContext.getSource();
    }
}
