package com.example.project_module5.service.impl;

import com.example.project_module5.dto.DataTickerDto;
import com.example.project_module5.dto.TickerDto;
import com.example.project_module5.entity.HistoryRequestStock;
import com.example.project_module5.entity.Ticker;
import com.example.project_module5.entity.User;
import com.example.project_module5.exception.IllegalTickerNameException;
import com.example.project_module5.repository.HistoryRequestStockRepository;
import com.example.project_module5.repository.TickerRepository;
import com.example.project_module5.repository.UserRepository;
import com.example.project_module5.service.TickerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TickerServiceImpl implements TickerService {
    private final TickerRepository tickerRepository;
    private final UserRepository userRepository;
    private final HistoryRequestStockRepository historyRequestStockRepository;
    private final ModelMapper modelMapper;

    public List<Ticker> findAll() {
        return tickerRepository.findAll();
    }

    @Override
    public TickerDto findByUsernameAndTickerName(String username, String tickerName) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user;
        List<Ticker> tickers = tickerRepository.findAllByName(tickerName);

        if (userOptional.isPresent()) {
            user = userOptional.get();

            List<HistoryRequestStock> allUserTickers = tickers.stream()
                    .map(ticker -> historyRequestStockRepository.findByUserAndTicker(user, ticker))
                    .toList();

            List<Ticker> userTickers = allUserTickers.stream()
                    .map(HistoryRequestStock::getTicker)
                    .toList();

            List<DataTickerDto> dataTickerDto = userTickers.stream()
                    .map(ticker -> modelMapper.map(ticker, DataTickerDto.class))
                    .toList();

            return TickerDto.builder().name(tickerName).data(dataTickerDto).build();
        }
        throw new IllegalTickerNameException("Не верно введено название акций");
    }

//    @Override
//    public List<Ticker> findByTickerName(String ticker) {
//        repository.findByName(ticker);
//        return repository.findByName(ticker);
//    }

}
