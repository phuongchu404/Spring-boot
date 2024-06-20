package com.oauth2.securityoauth.consts;

import org.springframework.http.HttpStatus;

public enum Error {
    TOKEN_INVALID( "token invalid", HttpStatus.UNAUTHORIZED);
    private String message;
    private HttpStatus httpStatus;
    public String getMessage() {
        return message;
    }
    Error(String message, HttpStatus httpStatus){
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
