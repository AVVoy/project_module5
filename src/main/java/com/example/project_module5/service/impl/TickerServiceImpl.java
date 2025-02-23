package com.example.project_module5.service.impl;

import com.example.project_module5.dto.DataTickerDto;
import com.example.project_module5.dto.SaveTickerRequest;
import com.example.project_module5.dto.SaveTickersRequest;
import com.example.project_module5.dto.TickerDto;
import com.example.project_module5.entity.HistoryRequestTicker;
import com.example.project_module5.entity.Ticker;
import com.example.project_module5.exception.IllegalTickerNameException;
import com.example.project_module5.repository.TickerRepository;
import com.example.project_module5.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TickerServiceImpl implements TickerService {
    private final TickerRepository tickerRepository;
    private final HistoryRequestTickerService historyRequestTickerService;
    private final ModelMapper modelMapper;
    private final PolygonService polygonService;
    private final DateService dateService;
    private final ObjectMapper objectMapper;

    public List<Ticker> findAll() {
        return tickerRepository.findAll();
    }

    @Override
    public TickerDto getUsersTickersByName(String tickerName) {

        List<HistoryRequestTicker> allUsersSavedTickers = historyRequestTickerService.findAllTickersByCurrentUser();

        List<Ticker> usersSavedTickersByName = getTickersFromHistory(allUsersSavedTickers, tickerName);

        if (usersSavedTickersByName.isEmpty()) {
            throw new IllegalTickerNameException("У пользователя нет сохраненных акций с таким именем!");
        }

        List<DataTickerDto> dataTickerDto = mapDataTickerDto(usersSavedTickersByName);

        return TickerDto.builder().name(tickerName).data(dataTickerDto).build();

    }

    private List<Ticker> getTickersFromHistory(List<HistoryRequestTicker> allUsersSavedTickers, String tickerName) {
        return allUsersSavedTickers.stream()
                .map(HistoryRequestTicker::getTicker)
                .filter(ticker -> ticker.getName().equals(tickerName))
                .toList();
    }

    private List<DataTickerDto> mapDataTickerDto(List<Ticker> userTickers) {
        return userTickers.stream()
                .map(ticker -> modelMapper.map(ticker, DataTickerDto.class))
                .toList();
    }

    @Override
    public void saveTicker(SaveTickerRequest request) {
        String tickerName = request.getName();
        LocalDate date = LocalDate.parse(request.getDate());

        Ticker ticker = tickerRepository.findByNameAndDate(tickerName, date);

        if (ticker != null) {
            HistoryRequestTicker userTicker = historyRequestTickerService.findByUserAndTicker(ticker);
            if (userTicker != null) {
                throw new IllegalArgumentException("Данные об акции уже существуют");
            } else {
                historyRequestTickerService.save(ticker);
            }
        } else {
            String polygonResponse = polygonService.findTicker(request);

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            TickerDto tickerDto;
            try {
                tickerDto = objectMapper.readValue(polygonResponse, TickerDto.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            save(tickerDto);
        }
    }

    @Override
    public void saveTickers(SaveTickersRequest request) {
        String tickerName = request.getName();
        LocalDate startDate = LocalDate.parse(request.getStart());
        LocalDate endDate = LocalDate.parse(request.getEnd());

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Неправильно введены даты. Начало диапазона должно быть раньше конца!");
        }

        List<LocalDate> rangeDate = dateService.getRange(startDate, endDate);

        for(LocalDate date : rangeDate) {
            saveTicker(SaveTickerRequest
                    .builder()
                    .name(tickerName)
                    .date(date.toString())
                    .build()
            );
        }
    }

    @Override
    public void save(TickerDto ticker) {
        for (DataTickerDto data : ticker.getData()) {
            Ticker savingTicker = modelMapper.map(data, Ticker.class);
            savingTicker.setName(ticker.getName());
            tickerRepository.save(savingTicker);

            historyRequestTickerService.save(savingTicker);



        }
    }
}
