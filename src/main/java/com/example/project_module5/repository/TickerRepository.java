package com.example.project_module5.repository;

import com.example.project_module5.entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TickerRepository extends JpaRepository<Ticker, Long> {

    List<Ticker> findAllByName(String tickerName);

    Ticker findByNameAndDate(String tickerName, LocalDate date);
}
