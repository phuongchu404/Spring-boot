package com.oauth2.securityoauth.controller;

import com.oauth2.securityoauth.dto.request.CreateUserRequest;
import com.oauth2.securityoauth.dto.request.LoginRequest;
import com.oauth2.securityoauth.dto.request.RegisterRequest;
import com.oauth2.securityoauth.service.RoleService;
import com.oauth2.securityoauth.service.SessionService;
import dev.samstevens.totp.exceptions.QrGenerationException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/add")
    //@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> addUser(@Valid @RequestBody CreateUserRequest request){
        return sessionService.addUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        return sessionService.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        return sessionService.logout(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) throws MessagingException, QrGenerationException {
        return sessionService.register(request);
    }


}
