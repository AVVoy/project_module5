package com.example.project_module5.controller;

import com.example.project_module5.dto.SaveTickerRequest;
import com.example.project_module5.dto.SaveTickersRequest;
import com.example.project_module5.dto.TickerDto;
import com.example.project_module5.exception.IllegalTickerNameException;
import com.example.project_module5.service.TickerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name = "Работа с тикерами акций")

public class UserTickerController {

    private final TickerService tickerService;

    @Operation(summary = "Получение сохраненных акций")
    @GetMapping("/stock/saved/{ticker}")
    public ResponseEntity getUsersTickersByName(@PathVariable("ticker") String tickerName) {
        TickerDto userTickers;
        try {
            userTickers = tickerService.getUsersTickersByName(tickerName);
        } catch (IllegalTickerNameException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userTickers, HttpStatus.OK);
    }

    @Operation(summary = "Сохранение акций за 1 день")
    @PostMapping("/stock/save")
    public ResponseEntity saveTicker(@RequestBody @Valid SaveTickerRequest saveTickerRequest) {
        try {
            tickerService.saveTicker(saveTickerRequest);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Сохранение акций за несколько дней")
    @PostMapping("/stocks/save")
    public ResponseEntity saveTickers(@RequestBody @Valid SaveTickersRequest saveTickersRequest) {
        try {
            tickerService.saveTickers(saveTickersRequest);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
