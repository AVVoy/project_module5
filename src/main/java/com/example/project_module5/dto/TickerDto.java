package com.example.project_module5.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class TickerDto {
    private Long id;
    private String name;
    private LocalDate date;
    private Double startPrice;
    private Double endPrice;
    private Double highPrice;
    private Double lowPrice;
}
