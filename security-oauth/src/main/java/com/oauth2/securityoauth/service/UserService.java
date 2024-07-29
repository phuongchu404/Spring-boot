package com.oauth2.securityoauth.service;

import com.oauth2.securityoauth.annotation.CurrentUser;
import com.oauth2.securityoauth.exception.InvalidTokenException;
import com.oauth2.securityoauth.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> getCurrentUser(String username);


}
