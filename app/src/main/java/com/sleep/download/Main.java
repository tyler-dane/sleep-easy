package com.sleep.download;

import com.google.api.client.util.Preconditions;
import com.sleep.settings.DownloaderSettings;

import java.io.IOException;

/**
 * This class holds the download project's main method.
 *
 * To run, execute "gradle -q run" from the project's root directory. You will be prompted to authenticate the first
 * time you run this.
 *
 * @author Ty Hitzeman
 */
public class Main {

    public static void main(String[] args) throws IOException {

        // Check before running
        Preconditions.checkArgument(
                !DownloaderSettings.BACKUP_DIRECTORY.startsWith("Enter "),
                "Please enter the download directory in Downloader.class");

        Downloader.setup();
    }
}
