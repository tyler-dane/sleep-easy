package com.sleep.download;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.sleep.settings.DownloaderSettings;

import java.io.IOException;

/**
 * This class builds and returns an authorized Drive client service.
 *
 * @author Ty Hitzeman
 */
public class DriveServiceBuilder {

    public static Drive buildDriveService() throws IOException {

        Credential credential = DriveAuthorizer.authorize();

        return new Drive.Builder(DownloaderSettings.HTTP_TRANSPORT, DownloaderSettings.JSON_FACTORY, credential).setApplicationName(DownloaderSettings.APPLICATION_NAME)
                .build();
    }
}
