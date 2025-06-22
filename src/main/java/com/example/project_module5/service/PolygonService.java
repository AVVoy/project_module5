package com.example.project_module5.service;

import com.example.project_module5.dto.SaveTickerRequest;
import com.example.project_module5.dto.SaveTickersRequest;
import com.example.project_module5.entity.Ticker;

import java.util.List;

public interface PolygonService {
    Ticker findTicker(SaveTickerRequest request);
    List<Ticker> findTickers(SaveTickersRequest request);
}

