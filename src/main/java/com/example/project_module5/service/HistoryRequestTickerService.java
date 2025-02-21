package com.example.project_module5.service;

import com.example.project_module5.entity.HistoryRequestTicker;
import com.example.project_module5.entity.Ticker;
import com.example.project_module5.entity.User;

import java.util.List;

public interface HistoryRequestTickerService {
    List<HistoryRequestTicker> findAllTickersByCurrentUser();

    HistoryRequestTicker findByUserAndTicker(User currentUser, Ticker ticker);

    void save(HistoryRequestTicker share);
}
