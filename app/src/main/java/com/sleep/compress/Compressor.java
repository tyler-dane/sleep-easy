package com.sleep.compress;

import com.sleep.download.Downloader;
import com.sleep.settings.DownloaderSettings;
import com.sleep.upload.Uploader;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class compresses Google files into a single .zip file
 */
public class Compressor {

    /**
     * Saves each google file to the backup directory
     *
     * @param backupDirPath
     * @param fileList
     */
    public static void addFilesToBackupDir(String backupDirPath, ArrayList fileList){

        try {
            FileOutputStream fos = new FileOutputStream(backupDirPath + DownloaderSettings.COMPRESSED_FILE_NAME);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (Object file : fileList) {
                zipFiles(file, zos);
            }

            zos.close();
            fos.close();

            Date date = new Date();

            //print summary to log. TODO change this to logger
            System.out.println("\n [" + String.format(DownloaderSettings.DATE_FORMAT, date) + "]");
            System.out.println("\t Zipped the following files in " + Downloader.backupDir + "/" +
                    DownloaderSettings.COMPRESSED_FILE_NAME + ": \n\t" + fileList);

            //TODO prompt user for whether they want to upload file
            // upload the compressed file, if desired
            Uploader.run();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Compresses all files in the backup directory
     *
     * @param file
     * @param zos
     * @throws IOException
     */
    public static void zipFiles(Object file, ZipOutputStream zos) throws IOException {

        FileInputStream fis = new FileInputStream(file.toString());
        ZipEntry zipEntry = new ZipEntry((String) file);
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;

        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }
}
