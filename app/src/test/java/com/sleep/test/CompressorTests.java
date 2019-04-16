package com.sleep.test;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.assertFalse;

/**
 * Tests functionality of the {@link com.sleep.compress.Compressor}
 * @author Ty Hitzeman
 */
public class CompressorTests {

    @Rule
    public TemporaryFolder backupDir = new TemporaryFolder();

    @Test
    public void testZip() {
        try {

            // create temp dir and file
            File testFile = backupDir.newFile("file1.docx");
            FileUtils.writeStringToFile(testFile, "Hello, World!");

            // Read it from temp file
            final String s = FileUtils.readFileToString(testFile);

            // Verify the content
            Assert.assertEquals("Hello, World!", s);


            FileOutputStream fos = new FileOutputStream(backupDir + "-Test.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            FileInputStream fis = new FileInputStream(testFile.toString());
            ZipEntry zipEntry = new ZipEntry(String.valueOf(testFile));
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;

            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }

            zos.closeEntry();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteFolder() throws IOException {
        File file = backupDir.newFile("testfolder");
        file.delete();
        assertFalse(file.exists());
    }
}
