package com.example.project_module5.service.impl;

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
    public List<TickerDto> findByUsernameAndTickerName(String username, String tickerName) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user;
        Ticker ticker = tickerRepository.findByName(tickerName);

        if (userOptional.isPresent()) {
            user = userOptional.get();

            HistoryRequestStock historyRequestStockByUser = historyRequestStockRepository.findByUser(user);
            List<HistoryRequestStock> allByUserAndTicker = historyRequestStockRepository.findAllByUserAndTicker(user, ticker);


            List<TickerDto> userTickers = allByUserAndTicker.stream()
                    .map(HistoryRequestStock::getTicker)
                    .map(ticker1 -> modelMapper.map(ticker1, TickerDto.class))
                    .toList();
            return userTickers;
        }
        throw new IllegalTickerNameException("Не верно введено название акций");
    }

//    @Override
//    public List<Ticker> findByTickerName(String ticker) {
//        repository.findByName(ticker);
//        return repository.findByName(ticker);
//    }

}
