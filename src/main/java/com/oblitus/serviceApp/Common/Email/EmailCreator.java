package com.oblitus.serviceApp.Common.Email;

import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailCreator {

    public static MimeMessage createEmail(
            String from,
            String to,
            String subject,
            String content
    ) throws MessagingException {
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipients(
                Message.RecipientType.TO,
                to);
        message.setSubject(subject);
        message.setText(content);
        return message;

    }
}
