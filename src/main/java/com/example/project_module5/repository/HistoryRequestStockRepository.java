package com.example.project_module5.repository;

import com.example.project_module5.entity.HistoryRequestStock;
import com.example.project_module5.entity.Ticker;
import com.example.project_module5.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRequestStockRepository extends JpaRepository<HistoryRequestStock, Long> {
    List<HistoryRequestStock> findAllByUserAndTicker(User user, Ticker ticker);

    HistoryRequestStock findByUser(User user);
}
