package com.oauth2.securityoauth.service;

import org.springframework.http.ResponseEntity;

public interface RoleService {
    ResponseEntity<?> findAll();
}
