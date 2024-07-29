package com.oauth2.securityoauth.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String message){
        super(message);
    }
    private Error error;
    public InvalidTokenException(Error error){
        super(error.getMessage());
        this.error = error;
    }
    public Error getError(){
         return this.error;
    }
    public InvalidTokenException(String message, Throwable throwable){
        super(message, throwable);
    }
}
