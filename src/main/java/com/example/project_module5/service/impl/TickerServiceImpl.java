package com.example.project_module5.service.impl;

import com.example.project_module5.dto.DataTickerDto;
import com.example.project_module5.dto.SaveTickerRequest;
import com.example.project_module5.dto.SaveTickersRequest;
import com.example.project_module5.dto.TickerDto;
import com.example.project_module5.entity.HistoryRequestTicker;
import com.example.project_module5.entity.Ticker;
import com.example.project_module5.exception.IllegalTickerNameException;
import com.example.project_module5.repository.TickerRepository;
import com.example.project_module5.service.HistoryRequestTickerService;
import com.example.project_module5.service.PolygonService;
import com.example.project_module5.service.TickerService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TickerServiceImpl implements TickerService {
    private final TickerRepository tickerRepository;
    private final HistoryRequestTickerService historyRequestTickerService;
    private final ModelMapper modelMapper;
    private final PolygonService polygonService;
    private final TickerServiceImpl selfProxy;

    public TickerServiceImpl(TickerRepository tickerRepository, HistoryRequestTickerService historyRequestTickerService, ModelMapper modelMapper, PolygonService polygonService, @Lazy TickerServiceImpl selfProxy) {
        this.tickerRepository = tickerRepository;
        this.historyRequestTickerService = historyRequestTickerService;
        this.modelMapper = modelMapper;
        this.polygonService = polygonService;
        this.selfProxy = selfProxy;
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

        Ticker ticker = tickerRepository.findTickerByNameAndDate(tickerName, date);

        if (ticker != null) {
            HistoryRequestTicker userTicker = historyRequestTickerService.findUserHistoryRequestByTicker(ticker);
            if (userTicker == null) {
                historyRequestTickerService.saveHistoryRequestTicker(ticker);
            }
        } else {
            Ticker savedNewTicker = polygonService.findTicker(request);

            selfProxy.saveNewTicker(savedNewTicker);
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

        List<LocalDate> rangeDate = startDate.datesUntil(endDate.plusDays(1))
                .toList();

        for (LocalDate date : rangeDate) {
            saveTicker(SaveTickerRequest
                    .builder()
                    .name(tickerName)
                    .date(date.toString())
                    .build()
            );
        }
    }

    @Transactional
    protected void saveNewTicker(Ticker savedTicker) {
        tickerRepository.save(savedTicker);
        historyRequestTickerService.saveHistoryRequestTicker(savedTicker);

    }
}
