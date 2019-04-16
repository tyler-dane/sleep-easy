package com.sleep.settings;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * This class contains static settings
 */
public class DownloaderSettings {

    /** Application name */
    public static final String APPLICATION_NAME = "SleepEasy Backups: A Drive API Java Client";

    /** Upload Main parent directory for application files*/
    public static java.io.File DRIVE_HOME;

    /** Directory to store downloads */
    public static String BACKUP_DIRECTORY = DRIVE_HOME + "/backups";

    /** Directory to store user credentials for this application.*/
    public static final java.io.File CREDENTIALS_DIR = new java.io.File(System.getProperty("user.home"),
            ".credentials/drive-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}.*/
    public static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory */
    public static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport */
    public static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by the download service.
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     */
    public static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);

    /** The maximum number of files to return per page */
    public static Integer MAX_PAGE_SIZE = 2;

    /** Date format that is used in the comprssed file name */
    public static String DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    /** Compressed file name suffix */
    public static String APP_SUFFIX = "_SleepEasyBackup.zip";

    /** Full compressed file name */
    public static String COMPRESSED_FILE_NAME = DATE_FORMAT + APP_SUFFIX;

}