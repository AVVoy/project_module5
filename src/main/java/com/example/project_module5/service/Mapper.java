package com.example.project_module5.service;

import com.example.project_module5.dto.TickerDto;
import com.example.project_module5.entity.Ticker;

import java.util.List;

public interface Mapper {
    TickerDto mapTickersToTickerDto(List<Ticker> tickers);

    public <D> D map(Object source, Class<D> destinationType);

}
