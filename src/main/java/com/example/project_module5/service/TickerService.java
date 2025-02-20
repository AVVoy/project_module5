package com.example.project_module5.service;

import com.example.project_module5.dto.SaveTickerRequest;
import com.example.project_module5.dto.TickerDto;
import com.example.project_module5.entity.Ticker;

import java.util.List;

public interface TickerService {

    List<Ticker> findAll();

//    List<Ticker> findByTickerName(String ticker);

    TickerDto getUsersTickersByName(String ticker);

    void saveTicker(SaveTickerRequest saveTickerRequest);
}
