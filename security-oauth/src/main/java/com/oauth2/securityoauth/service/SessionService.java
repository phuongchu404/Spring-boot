package com.oauth2.securityoauth.service;

import com.oauth2.securityoauth.dto.request.CreateUserRequest;
import com.oauth2.securityoauth.dto.request.LoginRequest;
import com.oauth2.securityoauth.dto.request.RegisterRequest;
import dev.samstevens.totp.exceptions.QrGenerationException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface SessionService {
    ResponseEntity<?> register(RegisterRequest request) throws MessagingException, QrGenerationException;

    ResponseEntity<?> addUser(CreateUserRequest request);

    ResponseEntity<?> login(LoginRequest request);

    ResponseEntity<?> logout(HttpServletRequest request);

//    ResponseEntity<?> verifyTotp(final String code, String username);
}
