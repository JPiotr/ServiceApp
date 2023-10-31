package com.oblitus.serviceApp.Common.Email;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.gmail.model.Message;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import java.io.IOException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class EmailSender {
    private static final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    private static final String appName = "Service Track";
    private static final String appEmail = "srvctrack@gmail.com";
    private final Gmail service;

    public EmailSender() throws Exception {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        service = new Gmail.Builder(httpTransport, jsonFactory, GoogleApiCredential.authorize())
                .setApplicationName(appName)
                .build();
    }

    public void sendEmail(String subject, String message, String toEmailAddress) throws IOException, MessagingException {

        // Encode as MIME message
        MimeMessage email = EmailCreator.createEmail(
                appEmail, toEmailAddress, subject, message
        );

        // Encode and wrap the MIME message into a gmail message
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message message1 = new Message();
        message1.setRaw(encodedEmail);
        try {
            // Create send message
            message1 = service.users().messages().send("me", message1).execute();

        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw e;
            }
        }
    }
}
