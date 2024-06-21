package com.oauth2.securityoauth.exception;

import com.oauth2.securityoauth.consts.Error;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseException extends RuntimeException{
    private final Error error;
    public ResponseException(Error error){
        super(error.getMessage());
        this.error = error;
    }
}
