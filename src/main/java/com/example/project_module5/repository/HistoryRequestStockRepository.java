package com.example.project_module5.repository;

import com.example.project_module5.entity.HistoryRequestShare;
import com.example.project_module5.entity.Ticker;
import com.example.project_module5.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRequestStockRepository extends JpaRepository<HistoryRequestShare, Long> {
    HistoryRequestShare findByUserAndTicker(User user, Ticker ticker);

//    HistoryRequestStock findByUser(User user);
}
