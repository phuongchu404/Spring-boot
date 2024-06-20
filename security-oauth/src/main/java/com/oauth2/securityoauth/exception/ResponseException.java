package com.oauth2.securityoauth.exception;

import com.oauth2.securityoauth.consts.Error;

public class ResponseException extends RuntimeException{
    private final Error error;
    public ResponseException(Error error){
        super(error.getMessage());
        this.error = error;
    }
}
