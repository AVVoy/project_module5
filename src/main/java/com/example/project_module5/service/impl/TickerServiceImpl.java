package com.example.project_module5.service.impl;

import com.example.project_module5.dto.DataTickerDto;
import com.example.project_module5.dto.SaveTickerRequest;
import com.example.project_module5.dto.TickerDto;
import com.example.project_module5.entity.HistoryRequestTicker;
import com.example.project_module5.entity.Ticker;
import com.example.project_module5.entity.User;
import com.example.project_module5.entity.UserTickerId;
import com.example.project_module5.exception.IllegalTickerNameException;
import com.example.project_module5.repository.TickerRepository;
import com.example.project_module5.service.HistoryRequestTickerService;
import com.example.project_module5.service.PolygonService;
import com.example.project_module5.service.TickerService;
import com.example.project_module5.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TickerServiceImpl implements TickerService {
    private final TickerRepository tickerRepository;
    private final HistoryRequestTickerService historyRequestTickerService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PolygonService polygonService;

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
            User currentUser = userService.getCurrentUser();
            HistoryRequestTicker userTicker = historyRequestTickerService.findByUserAndTicker(currentUser, ticker);
            if (userTicker != null) {
                throw new IllegalArgumentException("Данные об акции уже существуют");
            } else {
                UserTickerId userTickerId = UserTickerId.builder()
                        .userId(currentUser.getId())
                        .tickerId(ticker.getId())
                        .build();
                HistoryRequestTicker share = HistoryRequestTicker
                        .builder()
                        .userTickerId(userTickerId)
                        .user(currentUser)
                        .ticker(ticker)
                        .build();
                historyRequestTickerService.save(share);
            }
        } else {
            polygonService.save(request);
        }

    }
}
