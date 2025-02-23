package com.example.project_module5.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class TickerDto {

    @JsonProperty("ticker")
    private String name;

    @JsonProperty("results")
    List<DataTickerDto> data;

}
