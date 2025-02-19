package com.example.project_module5.repository;

import com.example.project_module5.entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TickerRepository extends JpaRepository<Ticker, Long> {

    List<Ticker> findAllByName(String tickerName);
}
