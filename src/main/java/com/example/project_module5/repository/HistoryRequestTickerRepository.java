package com.example.project_module5.repository;

import com.example.project_module5.entity.HistoryRequestTicker;
import com.example.project_module5.entity.Ticker;
import com.example.project_module5.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRequestTickerRepository extends JpaRepository<HistoryRequestTicker, Long> {
    HistoryRequestTicker findByUserAndTicker(User user, Ticker ticker);
    List<HistoryRequestTicker> findAllByUser(User user);

//    HistoryRequestStock findByUser(User user);
}
