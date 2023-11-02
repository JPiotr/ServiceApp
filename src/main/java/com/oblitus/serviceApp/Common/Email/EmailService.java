package com.oblitus.serviceApp.Common.Email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService { //todo: Interface?
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;
    public void sendEmail(String to, String subject, String content){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("srvctrack@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
