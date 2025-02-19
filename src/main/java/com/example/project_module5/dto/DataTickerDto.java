package com.example.project_module5.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class DataTickerDto {
    private LocalDate date;
    private Double startPrice;
    private Double endPrice;
    private Double highPrice;
    private Double lowPrice;
}
