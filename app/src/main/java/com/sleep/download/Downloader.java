package com.sleep.download;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import com.sleep.compress.Compressor;
import com.sleep.settings.DownloaderSettings;
import com.sleep.utilities.GFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class downloads Google files and then calls {@link Compressor} to
 * compress them into a single backup file.
 *
 * @author Ty Hitzeman
 */

public class Downloader {

    static {
        try {
            DownloaderSettings.HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DownloaderSettings.DATA_STORE_FACTORY = new FileDataStoreFactory(DownloaderSettings.CREDENTIALS_DIR);
            //TODO use or delete
            java.io.File DRIVE_HOME = new java.io.File(System.getProperty("user.home"),
                    "sleep-easy-backups");
            service = DriveServiceBuilder.buildDriveService();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public Downloader() throws IOException {
    }

    /** Instance of the Drive service */
    private static Drive service;

    /** List of Google files */
    private static ArrayList<String> fileList = new ArrayList<>();

    /** Downloader instance */
    private static MediaHttpDownloader downloader = new MediaHttpDownloader(DownloaderSettings.HTTP_TRANSPORT,
            service.getRequestFactory().getInitializer());

    /** Local parent directory for application files */
    private static java.io.File driveHome;

    /** Directory for application logs */
    private static java.io.File logDir;

    /** Local directory where the compressed backup will be saved */
    public static java.io.File backupDir;

    /** Full path to the backup directory */
    private static String backupDirPath;

    /** MimeType of the Google file */
    private static GFile.MIME_TYPE mimeType;

    /** Downloads files to the download directory */
    protected static void setup() throws IOException {

        // create application directories and save the path
        driveHome = new java.io.File(System.getProperty("user.home"),
                "sleep-easy-backups");
        driveHome.mkdir();

        backupDir = new java.io.File(driveHome + "/backups");
        backupDir.mkdir();
        backupDirPath = backupDir + "/";

        logDir = new java.io.File(driveHome + "/logs");
        logDir.mkdir();

        // set direct downloads to false to allow resumable downloads
        downloader.setDirectDownloadEnabled(false);

        // Save the names and IDs for up to x files.
        FileList result = service.files().list().setPageSize(DownloaderSettings.MAX_PAGE_SIZE)
                .setFields("nextPageToken, files(id, name, mimeType)").execute();

        //Save files in a list, print, and download
        List<File> files = result.getFiles();

        if (files == null || files.size() == 0) {
            System.out.println("No Drive files found.");
        } else {

            for (File file : files) {
                updateFileName(file, fileList);
            }

            // After updating and downloading all the Drive files,
            // start compressing them into a backup file
            Compressor.addFilesToBackupDir(backupDirPath, fileList);
        }
    }

    /** Uses the Google file's MimeType to update its name with an appropriate extension */
    private static void updateFileName(File file, ArrayList<String> fileList) throws IOException {

        mimeType = GFile.MIME_TYPE.parseMimeType(file.getMimeType());

        final String extension = GFile.MIME_TYPE.parseExtension(mimeType);

        String updatedFileName = file.getName() + extension;

        fileList.add(updatedFileName);

        downloadFile(file, updatedFileName);
    }

    /** Downloads the Google file using its updated filename, then calls {@link Compressor} to start compressing files */
    private static void downloadFile(File file, String updatedFileName) throws IOException {

        OutputStream os = new FileOutputStream(updatedFileName);

        //todo figure out why this is using /pdf (?)
        service.files().export(file.getId(), "application/pdf")
                .executeMediaAndDownloadTo(os);
    }
}

