package com.example.project_module5.service;

import com.example.project_module5.dto.JwtAuthenticationResponse;
import com.example.project_module5.dto.SignInRequest;
import com.example.project_module5.dto.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest request);
    JwtAuthenticationResponse signIn(SignInRequest request);
}
