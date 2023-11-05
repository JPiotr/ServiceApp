package com.oblitus.serviceApp.Common.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService { //todo: Interface?
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
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

    public void sendHtmlEmail(String to, String subject, Map<String, Object> model, String template){
        final Context context = new Context();
        context.setVariables(model);
        String content = templateEngine.process(template, context);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom("srvctrack@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
