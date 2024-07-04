package com.oauth2.securityoauth.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationProcessingException extends AuthenticationException {
    public OAuth2AuthenticationProcessingException(String message, Throwable throwable){
        super(message, throwable);
    }
    public OAuth2AuthenticationProcessingException(String message){
        super(message);
    }
}
