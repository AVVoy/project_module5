package com.example.project_module5.service.impl;

import com.example.project_module5.dto.SaveTickerRequest;
import com.example.project_module5.dto.SaveTickersRequest;
import com.example.project_module5.dto.TickerDto;
import com.example.project_module5.entity.HistoryRequestTicker;
import com.example.project_module5.entity.Ticker;
import com.example.project_module5.exception.IllegalDateException;
import com.example.project_module5.exception.TickerNotFoundException;
import com.example.project_module5.repository.TickerRepository;
import com.example.project_module5.service.HistoryRequestTickerService;
import com.example.project_module5.service.Mapper;
import com.example.project_module5.service.PolygonService;
import com.example.project_module5.service.TickerService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TickerServiceImpl implements TickerService {
    private final TickerRepository tickerRepository;
    private final HistoryRequestTickerService historyRequestTickerService;
    private final Mapper mapper;
    private final PolygonService polygonService;
    private final TickerServiceImpl selfProxy;

    public TickerServiceImpl(TickerRepository tickerRepository, HistoryRequestTickerService historyRequestTickerService, Mapper mapper, PolygonService polygonService, @Lazy TickerServiceImpl selfProxy) {
        this.tickerRepository = tickerRepository;
        this.historyRequestTickerService = historyRequestTickerService;
        this.mapper = mapper;
        this.polygonService = polygonService;
        this.selfProxy = selfProxy;
    }

    @Override
    public TickerDto getUserTickersByName(String tickerName) {

        List<HistoryRequestTicker> allUserSavedTickers = historyRequestTickerService.findAllTickersByCurrentUser();

        List<Ticker> userSavedTickersByName = getTickersByNameFromHistoryRequest(allUserSavedTickers, tickerName);

        if (userSavedTickersByName.isEmpty()) {
            throw new TickerNotFoundException("У пользователя нет сохраненных акций с таким именем!");
        }

        return mapper.mapTickersToTickerDto(userSavedTickersByName);

    }

    private List<Ticker> getTickersByNameFromHistoryRequest(List<HistoryRequestTicker> allUserSavedTickers, String tickerName) {
        return allUserSavedTickers.stream()
                .map(HistoryRequestTicker::getTicker)
                .filter(ticker -> ticker.getName().equals(tickerName))
                .toList();
    }



    @Override
    public Ticker saveTicker(SaveTickerRequest request) {
        String tickerName = request.getName();
        LocalDate date = LocalDate.parse(request.getDate());

        Ticker ticker = tickerRepository.findTickerByNameAndDate(tickerName, date);

        if (ticker != null) {
            HistoryRequestTicker userTicker = historyRequestTickerService.findHistoryRequestForCurrentUserByTicker(ticker);
            if (userTicker == null) {
                historyRequestTickerService.saveHistoryRequestTicker(ticker);
            }
        } else {
            ticker = polygonService.findTicker(request);

            selfProxy.saveNewTicker(ticker);
        }

        return ticker;
    }

    @Override
    public void saveTickers(SaveTickersRequest request) {
        String tickerName = request.getName();
        LocalDate startDate = LocalDate.parse(request.getStart());
        LocalDate endDate = LocalDate.parse(request.getEnd());

        if (endDate.isBefore(startDate)) {
            throw new IllegalDateException("Неправильно введены даты. Начало диапазона должно быть раньше конца!");
        }

        //TODO: запрос за отрезок времени

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
