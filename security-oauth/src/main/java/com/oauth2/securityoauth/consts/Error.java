package com.oauth2.securityoauth.consts;

import org.springframework.http.HttpStatus;

public enum Error {
    TOKEN_INVALID( "token invalid", HttpStatus.UNAUTHORIZED),
    USERNAME_ALREADY_EXIST( "username is already exists", HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXIST( "email is already in use",HttpStatus.CONFLICT),
    ROLE_NOT_FOUND( "role is not found", HttpStatus.NOT_FOUND),
    INCORRECT_USERNAME( "incorrect username", HttpStatus.UNAUTHORIZED),
    INCORRECT_PASSWORD( "incorrect password", HttpStatus.UNAUTHORIZED),
    RESOURCE_NOT_FOUND( "User not found by id", HttpStatus.NOT_FOUND)
    ;
    private String message;

    private HttpStatus httpStatus;

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    Error(String message, HttpStatus httpStatus){
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
