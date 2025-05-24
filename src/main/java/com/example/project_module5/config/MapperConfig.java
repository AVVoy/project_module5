package com.example.project_module5.config;

import com.example.project_module5.mapper.LocalDateConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE);
        modelMapper.addConverter(new LocalDateConverter());
        return modelMapper;
    }
}
