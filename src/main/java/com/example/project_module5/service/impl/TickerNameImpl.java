package com.example.project_module5.service.impl;

import com.example.project_module5.entity.TickerName;
import com.example.project_module5.repository.TickerNameRepository;
import com.example.project_module5.service.TickerNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TickerNameImpl implements TickerNameService {

    private final TickerNameRepository repository;

    @Override
    public void save(TickerName tickerName) {
        repository.save(tickerName);
    }

    @Override
    public boolean validate(String tickerName) {

        List<TickerName> TickersName = repository.findByName(tickerName);

        return !tickerName.isEmpty();
    }
}
