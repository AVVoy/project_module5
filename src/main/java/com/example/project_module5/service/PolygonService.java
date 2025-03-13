package com.example.project_module5.service;

import com.example.project_module5.dto.SaveTickerRequest;
import com.example.project_module5.entity.Ticker;

public interface PolygonService {
    Ticker findTicker(SaveTickerRequest request);
}
