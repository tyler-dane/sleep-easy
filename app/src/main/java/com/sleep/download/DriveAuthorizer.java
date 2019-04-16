package com.sleep.download;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.sleep.settings.DownloaderSettings;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * This class creates an authorized Credential object and saves the client_secret.json file
 *
 * @author Ty Hitzeman
 */
public class DriveAuthorizer {

    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = Downloader.class.getResourceAsStream("/client_secret.json");

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(DownloaderSettings.JSON_FACTORY, new InputStreamReader(in));

        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=drive "
                            + "into drive-cmdline-sample/src/main/resources/client_secrets.json");
            System.exit(1);
        }

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(DownloaderSettings.HTTP_TRANSPORT, DownloaderSettings.JSON_FACTORY,
                clientSecrets, DownloaderSettings.SCOPES).setDataStoreFactory(DownloaderSettings.DATA_STORE_FACTORY).setAccessType("offline")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
                .authorize("user");

        System.out.println("Credentials saved to " + DownloaderSettings.CREDENTIALS_DIR.getAbsolutePath());

        return credential;
    }
}
