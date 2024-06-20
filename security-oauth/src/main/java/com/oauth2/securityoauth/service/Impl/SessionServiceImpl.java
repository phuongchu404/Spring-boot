package com.oauth2.securityoauth.service.Impl;

import com.oauth2.securityoauth.dto.request.LoginRequest;
import com.oauth2.securityoauth.dto.request.RegisterRequest;
import com.oauth2.securityoauth.service.SessionService;
import org.springframework.http.ResponseEntity;

public class SessionServiceImpl implements SessionService {
    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        return null;
    }
}
