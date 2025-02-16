package com.example.project_module5.service.impl;

import com.example.project_module5.entity.Ticker;
import com.example.project_module5.repository.TickerRepository;
import com.example.project_module5.service.TickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TickerServiceImpl implements TickerService {
    private final TickerRepository repository;

    public List<Ticker> findAll() {
        return repository.findAll();
    }

}
