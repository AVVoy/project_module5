package com.example.project_module5.service.impl;

import com.example.project_module5.dto.CustomBarTicker;
import com.example.project_module5.dto.CustomBarTickers;
import com.example.project_module5.dto.DataTickerDto;
import com.example.project_module5.dto.TickerDto;
import com.example.project_module5.entity.Ticker;
import com.example.project_module5.service.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapperImpl implements Mapper {

    private final ModelMapper modelMapper;

    public TickerDto mapTickersToTickerDto(List<Ticker> tickers) {

        String tickerName = tickers.get(0).getName();
        boolean isValidData = tickers.stream()
                .allMatch(ticker -> ticker.getName().equals(tickerName));

        if (!isValidData)
            throw new IllegalArgumentException("Некорректные данные. Акции должны быть от одной кампании!");

        List<DataTickerDto> dataTickerDto = tickers.stream()
                .map(ticker -> modelMapper.map(ticker, DataTickerDto.class))
                .toList();

        return TickerDto.builder()
                .name(tickerName)
                .data(dataTickerDto)
                .build();
    }

    public <D> D map(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    @Override
    public List<Ticker> mapCustomBarTickersToTicker(CustomBarTickers tickersDto) {
        if (tickersDto == null || tickersDto.getTickerList().isEmpty()) {
            throw new IllegalArgumentException("Список акций пуст");
        }
        List<Ticker> tickers = new ArrayList<>();
        String nameTicker = tickersDto.getName();
        for (CustomBarTicker tickerDto : tickersDto.getTickerList()) {
            Ticker ticker = modelMapper.map(tickerDto, Ticker.class);
            ticker.setName(nameTicker);
            tickers.add(ticker);
        }

        return tickers;
    }
}
