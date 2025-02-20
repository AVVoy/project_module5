package com.example.project_module5.service.impl;

import com.example.project_module5.dto.DataTickerDto;
import com.example.project_module5.dto.SaveTickerRequest;
import com.example.project_module5.dto.TickerDto;
import com.example.project_module5.entity.HistoryRequestShare;
import com.example.project_module5.entity.Ticker;
import com.example.project_module5.entity.User;
import com.example.project_module5.entity.UserTickerId;
import com.example.project_module5.exception.IllegalTickerNameException;
import com.example.project_module5.repository.HistoryRequestStockRepository;
import com.example.project_module5.repository.TickerRepository;
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
    private final HistoryRequestStockRepository historyRequestStockRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PolygonService polygonService;

    public List<Ticker> findAll() {
        return tickerRepository.findAll();
    }

    @Override
    public TickerDto getUsersTickersByName(String tickerName) {

        //TODO: акций в БД мб много, мб сначала получать акции юзера, а потом отбирать по имени
        List<Ticker> allTickersWithTickerName = tickerRepository.findAllByName(tickerName);

        List<HistoryRequestShare> historyRequestShares = getUsersTicker(allTickersWithTickerName);

        List<Ticker> userTickers = getTickerFromShare(historyRequestShares);

        if (userTickers.isEmpty()) throw new IllegalTickerNameException("Не верно введено название акций");

        List<DataTickerDto> dataTickerDto = mapDataTickerDto(userTickers);

        return TickerDto.builder().name(tickerName).data(dataTickerDto).build();

    }

    private List<HistoryRequestShare> getUsersTicker(List<Ticker> tickers) {
        User currentUser = userService.getCurrentUser();
        return tickers.stream()
                .map(ticker -> historyRequestStockRepository.findByUserAndTicker(currentUser, ticker))
                .filter(Objects::nonNull)
                .toList();
    }

    private List<Ticker> getTickerFromShare(List<HistoryRequestShare> historyRequestShares) {
        return historyRequestShares.stream()
                .map(HistoryRequestShare::getTicker)
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
            HistoryRequestShare userTicker = historyRequestStockRepository.findByUserAndTicker(currentUser, ticker);
            if (userTicker != null) {
                throw new IllegalArgumentException("Данные об акции уже существуют");
            } else {
                UserTickerId userTickerId = UserTickerId.builder()
                        .userId(currentUser.getId())
                        .tickerId(ticker.getId())
                        .build();
                HistoryRequestShare share = HistoryRequestShare
                        .builder()
                        .userTickerId(userTickerId)
                        .user(currentUser)
                        .ticker(ticker)
                        .build();
                historyRequestStockRepository.save(share);
            }
        } else {
            polygonService.save(request);
        }

    }
}
