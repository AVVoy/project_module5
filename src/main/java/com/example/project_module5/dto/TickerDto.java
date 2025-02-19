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
    private String name;
    @JsonProperty("data")
    List<DataTickerDto> data;

}
