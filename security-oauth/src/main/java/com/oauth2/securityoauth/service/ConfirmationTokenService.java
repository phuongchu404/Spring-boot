package com.oauth2.securityoauth.service;

import com.oauth2.securityoauth.exception.InvalidTokenException;

public interface ConfirmationTokenService {
    boolean verifyUser(final String token) throws InvalidTokenException;
}
