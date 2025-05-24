package com.example.project_module5.service;

import com.example.project_module5.dto.SaveTickerRequest;
import com.example.project_module5.dto.SaveTickersRequest;
import com.example.project_module5.dto.TickerDto;
import com.example.project_module5.entity.Ticker;

public interface TickerService {

    TickerDto getUserTickersByName(String ticker);

    Ticker saveTicker(SaveTickerRequest saveTickerRequest);

    void saveTickers(SaveTickersRequest saveTickersRequest);

}
