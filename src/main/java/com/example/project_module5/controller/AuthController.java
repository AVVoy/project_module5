package com.example.project_module5.controller;

import com.example.project_module5.dto.JwtAuthenticationResponse;
import com.example.project_module5.dto.SignInRequest;
import com.example.project_module5.dto.SignUpRequest;
import com.example.project_module5.exception.IllegalEmailException;
import com.example.project_module5.exception.IllegalUsernameException;
import com.example.project_module5.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody @Valid SignUpRequest request) {
        JwtAuthenticationResponse response;
        try {
            response = authenticationService.signUp(request);
        } catch (IllegalUsernameException | IllegalEmailException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody @Valid SignInRequest request) {
        JwtAuthenticationResponse response;

        try {
            response = authenticationService.signIn(request);
        } catch (UsernameNotFoundException e) {
           return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
