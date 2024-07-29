package com.oauth2.securityoauth.service.Impl;

import com.oauth2.securityoauth.entity.ConfirmationToken;
import com.oauth2.securityoauth.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendEmail(String to, String subject, String template, Context context) throws MessagingException {
        MimeMessage mimeMailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true);
        String htmlContent = templateEngine.process(template, context);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        mailSender.send(mimeMailMessage);
    }

    @Override
    public void sendConfirmationEmail(ConfirmationToken confirmationToken) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(confirmationToken.getUser().getEmail());
        helper.setSubject("Xác nhận Email - Kích hoạt tài khoản");
        helper.setText("<html>" +
                "<body>" +
                "<h2>Dear "+ confirmationToken.getUser().getUsername() + ",</h2>"
                + "<br/> We're excited to have you get started. " +
                "Please click on below link to confirm your account."
                + "<br/> "  + generateConfirmationLink(confirmationToken.getConfirmationToken())+"" +
                "<br/> Regards,<br/>" +
                "MFA Registration team" +
                "</body>" +
                "</html>");
    }

    private String generateConfirmationLink(String token){
        return "<a href=http://localhost:8080/confirm-email?token=\"+token+\">Confirm Email</a>";
    }
}
