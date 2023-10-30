package com.oblitus.serviceApp.Common.Email;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Objects;

public class GoogleApiCredential {
    private static final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    private static final NetHttpTransport netHttpTransport = new NetHttpTransport();
    private static final DataStoreFactory dataStoreFactory;

    static {
        try {
            dataStoreFactory = new FileDataStoreFactory(Paths.get("token").toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Value("${application.sec.gapi.client-id}")
    private static String apiClientId;
    @Value("${application.sec.gapi.client-secret}")
    private static String apiClientSecret;

    public static Credential authorize()throws Exception {
        // set up authorization code flow

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                netHttpTransport, jsonFactory,
                apiClientId, apiClientSecret,
                Collections.singleton(GmailScopes.GMAIL_SEND))
                .setDataStoreFactory(
                        dataStoreFactory)
                .setAccessType("offline").build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
}
