package com.oauth2.securityoauth.service;

import com.oauth2.securityoauth.dto.request.LoginRequest;
import com.oauth2.securityoauth.dto.request.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface SessionService {
    ResponseEntity<?> register(RegisterRequest request);

    ResponseEntity<?> login(LoginRequest request);
}
