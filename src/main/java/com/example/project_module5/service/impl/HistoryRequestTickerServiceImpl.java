package com.example.project_module5.service.impl;

import com.example.project_module5.entity.HistoryRequestTicker;
import com.example.project_module5.entity.Ticker;
import com.example.project_module5.entity.User;
import com.example.project_module5.entity.UserTickerId;
import com.example.project_module5.repository.HistoryRequestTickerRepository;
import com.example.project_module5.service.HistoryRequestTickerService;
import com.example.project_module5.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryRequestTickerServiceImpl implements HistoryRequestTickerService {

    private final HistoryRequestTickerRepository repository;
    private final UserService userService;

    public List<HistoryRequestTicker> findAllTickersByCurrentUser() {
        User currentUser = userService.getCurrentUser();
        return repository.findAllByUser(currentUser);
    }

    @Override
    public HistoryRequestTicker findByUserAndTicker(Ticker ticker) {
        User currentUser = userService.getCurrentUser();
        return  repository.findByUserAndTicker(currentUser, ticker);
    }

    @Override
    public void save(Ticker ticker) {
        User currentUser = userService.getCurrentUser();
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
        repository.save(share);
    }
}
