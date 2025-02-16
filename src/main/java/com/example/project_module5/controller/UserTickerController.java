package com.example.project_module5.controller;

import com.example.project_module5.service.TickerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/stock")
@RequiredArgsConstructor
@Tag(name = "Работа с тикерами акций")

public class UserTickerController {

    private final TickerService tickerService;

    @Operation(summary = "Получение сохраненных акций")
    @GetMapping
    public ResponseEntity<Void> getStock() {
        tickerService.findAll();
        return ResponseEntity.ok().build();
    }
}
