package com.example.project_module5.service;

import com.example.project_module5.entity.TickerName;

public interface TickerNameService {
    void save(TickerName tickerName);

    boolean validate(String tickerName);
}
