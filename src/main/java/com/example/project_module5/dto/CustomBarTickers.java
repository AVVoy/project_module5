package com.example.project_module5.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CustomBarTickers {
    @JsonProperty("ticker")
    private String name;

    @JsonProperty("results")
    List<CustomBarTicker> tickerList;
}
