package com.oauth2.securityoauth.service;

import com.oauth2.securityoauth.entity.ConfirmationToken;
import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface EmailService {
    void sendEmail(String to, String subject, String template, Context context) throws MessagingException;

    void sendConfirmationEmail(ConfirmationToken confirmationToken) throws MessagingException;
}
