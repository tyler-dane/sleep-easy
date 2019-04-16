package com.sleep.test;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.sleep.settings.DownloaderSettings;
import com.sleep.utilities.GFile;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class DownloaderTests {

    java.io.File parentDir = new java.io.File("/tmp");
    GFile.MIME_TYPE mimeType;

    @Rule
    public TemporaryFolder backupDir = new TemporaryFolder();


    static {
        try {
            DownloaderSettings.HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DownloaderSettings.DATA_STORE_FACTORY = new FileDataStoreFactory(DownloaderSettings.CREDENTIALS_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    @Test
    public void testSetup() throws IOException {

        if (!parentDir.exists() && !parentDir.mkdirs()) {
            try {
                assertTrue(false);
                throw new IOException("ERROR: Unable to create parent directory");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            assertTrue(true);
        }
    }

    @Test
    public void testJSONPayload() {

        ArrayList documentList = new ArrayList<>();
        ArrayList payload = new ArrayList<>();

        GenericJson document1 = new GenericJson();
        GenericJson document2 = new GenericJson();
        GenericJson document3 = new GenericJson();

        document1.put("id", "893jdjzhYRwZMMth3aCHoKpKiLVWu7D87ZxbDIOC8GI");
        document1.put("mimeType", "application/vnd.google-apps.document");
        document1.put("name", "The Sun Also Rises");

        document2.put("id", "8j2QZTi-_YRwZMMth3aCHokiDiLVWu7D07ZhbDXAU8GI");
        document2.put("mimeType", "application/vnd.google-apps.video");
        document2.put("name", "Charlie Bit Me!");

        document3.put("id", "1FDMF1Isd1SHUK4YDPj6SZYqmcm1U1wepdwG9fn490n8");
        document3.put("mimeType", "application/vnd.google-apps.spreadsheet");
        document3.put("name", "NASDAQ Stocks");

        documentList.add(0, document1);
        documentList.add(1, document2);
        documentList.add(2, document3);

        payload.add(0, documentList);
        payload.toString();

        System.out.println("payload = " + payload);

        //TODO get Gson import working so this can be converted to JSON
        //String json = new Gson().toJson(payload);
    }

    @Test
    public void testUpdateFileName() throws IOException {
        ArrayList<String> fileNames = new ArrayList<>();
        ArrayList<String> extensions = new ArrayList<>();

        fileNames.add(0, "simplefile");
        fileNames.add(1, "CapitalizedFile");
        fileNames.add(2, "File with Spaces");
        fileNames.add(3, "File-with-dashes");
        fileNames.add(4, "File_With_Underscores");
        fileNames.add(5, "001");

        extensions.add(0, ".docx");
        extensions.add(1, ".xlsx");

        for (String fileName: fileNames) {
            mimeType = GFile.MIME_TYPE.parseMimeType("application/vnd.google-apps.document");
            String extension = ".docx";
            String updatedFileName = fileName + extension;
            File tempFile = backupDir.newFile(updatedFileName);
        }
    }
}
