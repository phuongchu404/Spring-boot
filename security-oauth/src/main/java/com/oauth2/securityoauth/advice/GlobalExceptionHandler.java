package com.oauth2.securityoauth.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException exception){
        Map<String, Object> errors = new HashMap<>();
        List<String> messages = new ArrayList<>();
//        exception.getBindingResult().getAllErrors().forEach(error ->{
//            String fieldName =((FieldError) error).getField();;
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
        //return ResponseEntity.badRequest().body(exception.getBindingResult().getFieldError().getDefaultMessage());
        exception.getBindingResult().getAllErrors().forEach(error ->{
            String errorMessage = error.getDefaultMessage();
            messages.add(errorMessage);
        });
        errors.put("message", messages);
        return ResponseEntity.badRequest().body(errors);
    }
}
