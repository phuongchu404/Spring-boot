package com.oauth2.securityoauth.service.Impl;

import com.oauth2.securityoauth.entity.ConfirmationToken;
import com.oauth2.securityoauth.entity.User;
import com.oauth2.securityoauth.exception.InvalidTokenException;
import com.oauth2.securityoauth.repo.ConfirmationTokenRepository;
import com.oauth2.securityoauth.repo.UserRepository;
import com.oauth2.securityoauth.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean verifyUser(String token) throws InvalidTokenException {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token).orElseThrow(() -> new InvalidTokenException("Token is not valid"));
        User user = confirmationToken.getUser();
        if(Objects.isNull(user)) {
            return false;
        }
        user.setEmailVerified(true);
        userRepository.save(user);
        confirmationTokenRepository.delete(confirmationToken);
        return true;
    }
}
