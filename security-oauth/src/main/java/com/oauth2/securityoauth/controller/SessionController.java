package com.oauth2.securityoauth.controller;

import com.oauth2.securityoauth.dto.request.CreateUserRequest;
import com.oauth2.securityoauth.dto.request.LoginRequest;
import com.oauth2.securityoauth.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody CreateUserRequest request){
        return sessionService.addUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        return sessionService.login(request);
    }
}
