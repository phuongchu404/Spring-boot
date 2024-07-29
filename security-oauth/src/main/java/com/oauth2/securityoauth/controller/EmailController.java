package com.oauth2.securityoauth.controller;

import com.oauth2.securityoauth.exception.InvalidTokenException;
import com.oauth2.securityoauth.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @GetMapping("/confirm-email")
    public ResponseEntity<?> confirmEmail(@RequestParam("token") String token) throws InvalidTokenException {
        try {
            if(confirmationTokenService.verifyUser(token)){
                return ResponseEntity.ok("Your email has been successfully verified.");
            }else{
                return ResponseEntity.ok("User details not found. Please login and regenerate the confirmation link.");
            }
        }catch (InvalidTokenException exception){
            throw new InvalidTokenException("Link expired or token already verified.");
        }
    }
}
