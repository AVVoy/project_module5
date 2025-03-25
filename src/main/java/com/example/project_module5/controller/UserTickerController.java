package com.example.project_module5.controller;

import com.example.project_module5.dto.SaveTickerRequest;
import com.example.project_module5.dto.SaveTickersRequest;
import com.example.project_module5.dto.TickerDto;
import com.example.project_module5.exception.TickerNameNotFoundException;
import com.example.project_module5.service.TickerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name = "Работа с тикерами акций")

public class UserTickerController {

    private final TickerService tickerService;

    @Operation(summary = "Получение сохраненных акций")
    @GetMapping("/stock/saved/{ticker}")
    public ResponseEntity<TickerDto> getUsersTickersByName(@PathVariable("ticker") String tickerName) {
        TickerDto userTickers = tickerService.getUserTickersByName(tickerName);

        return new ResponseEntity<>(userTickers, HttpStatus.OK);
    }

    @Operation(summary = "Сохранение акций за 1 день")
    @PostMapping("/stock/save")
    public ResponseEntity saveTicker(@RequestBody @Valid SaveTickerRequest savedTickerRequest) {
        tickerService.saveTicker(savedTickerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Сохранение акций за несколько дней")
    @PostMapping("/stocks/save")
    public ResponseEntity saveTickers(@RequestBody @Valid SaveTickersRequest saveTickersRequest) {
        tickerService.saveTickers(saveTickersRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
