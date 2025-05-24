package com.example.project_module5.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DataTickerDto {

    private LocalDate date;
    private Double startPrice;
    private Double endPrice;
    private Double highPrice;
    private Double lowPrice;
}
