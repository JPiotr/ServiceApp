package com.oblitus.serviceApp.Common.Email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
