package com.oauth2.securityoauth.service.Impl;

import com.oauth2.securityoauth.consts.Error;
import com.oauth2.securityoauth.entity.ConfirmationToken;
import com.oauth2.securityoauth.entity.User;
import com.oauth2.securityoauth.exception.InvalidTokenException;
import com.oauth2.securityoauth.exception.ResponseException;
import com.oauth2.securityoauth.repo.UserRepository;
import com.oauth2.securityoauth.security.UserDetailsImpl;
import com.oauth2.securityoauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> getCurrentUser(String username) {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new ResponseException(Error.RESOURCE_NOT_FOUND));
        return ResponseEntity.ok(user);
    }


}
