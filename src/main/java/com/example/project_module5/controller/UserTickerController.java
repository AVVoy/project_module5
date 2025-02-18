package com.example.project_module5.controller;

import com.example.project_module5.dto.TickerDto;
import com.example.project_module5.entity.Ticker;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user/stock")
@RequiredArgsConstructor
@Tag(name = "Работа с тикерами акций")

public class UserTickerController {

    private final TickerService tickerService;

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Operation(summary = "Получение сохраненных акций")
    @GetMapping("/saved/{ticker}")
    public ResponseEntity getStock(@PathVariable("ticker") String ticker) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();
        List<TickerDto> byUsernameAndTickerName;
        try {
            byUsernameAndTickerName = tickerService.findByUsernameAndTickerName(username, ticker);
        } catch (IllegalTickerNameException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(byUsernameAndTickerName, HttpStatus.OK);
    }
}
