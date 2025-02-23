package com.example.project_module5.repository;

import com.example.project_module5.entity.TickerName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TickerNameRepository extends JpaRepository<TickerName, Long> {
    List<TickerName> findByName(String tickerName);
}
