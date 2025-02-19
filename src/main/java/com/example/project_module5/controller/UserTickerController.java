package com.example.project_module5.controller;

import com.example.project_module5.dto.TickerDto;
import com.example.project_module5.entity.User;
import com.example.project_module5.exception.IllegalTickerNameException;
import com.example.project_module5.repository.UserRepository;
import com.example.project_module5.service.JwtService;
import com.example.project_module5.service.TickerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/stock")
@RequiredArgsConstructor
@Tag(name = "Работа с тикерами акций")

public class UserTickerController {

    private final TickerService tickerService;

    @Operation(summary = "Получение сохраненных акций")
    @GetMapping("/saved/{ticker}")
    public ResponseEntity getStock(@PathVariable("ticker") String tickerName) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();
        TickerDto userTickers;
        try {
            userTickers = tickerService.findByUsernameAndTickerName(username, tickerName);
        } catch (IllegalTickerNameException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userTickers, HttpStatus.OK);
    }
}
