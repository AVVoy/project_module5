package com.example.project_module5.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class DailyOpenCloseTicker {

    @JsonProperty("symbol")
    private String name;

    @JsonProperty("from")
    private LocalDate date;

    @JsonProperty("open")
    private Double startPrice;

    @JsonProperty("close")
    private Double endPrice;

    @JsonProperty("high")
    private Double highPrice;

    @JsonProperty("low")
    private Double lowPrice;
}
