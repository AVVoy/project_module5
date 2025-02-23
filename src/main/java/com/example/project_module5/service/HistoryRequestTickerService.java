package com.example.project_module5.service;

import com.example.project_module5.entity.HistoryRequestTicker;
import com.example.project_module5.entity.Ticker;

import java.util.List;

public interface HistoryRequestTickerService {
    List<HistoryRequestTicker> findAllTickersByCurrentUser();

    HistoryRequestTicker findByUserAndTicker(Ticker ticker);

    void save(Ticker ticker);
}
