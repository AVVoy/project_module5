package com.example.project_module5.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class CustomBarTicker {
    @JsonProperty("t")
    private LocalDate date;

    @JsonProperty("o")
    private Double startPrice;

    @JsonProperty("c")
    private Double endPrice;

    @JsonProperty("h")
    private Double highPrice;

    @JsonProperty("l")
    private Double lowPrice;

    public void setDate(long date) {
        this.date = LocalDate.ofInstant(Instant.ofEpochMilli(date), ZoneId.of("Europe/Moscow"));
    }
}
