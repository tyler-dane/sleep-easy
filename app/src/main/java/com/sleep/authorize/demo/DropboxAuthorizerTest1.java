package com.sleep.authorize.demo;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.json.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DropboxAuthorizerTest1 {

    public static void run() throws IOException {
        //TODO update
        System.out.println("in authorizer authorize run");

        String argAppInfoFile = "/Users/ty/tbd";
        String argAuthFileOutput = "/Users/ty/tbd2";

        // Read app info file (contains app key and app secret)
        DbxAppInfo appInfo;
        try {
            appInfo = DbxAppInfo.Reader.readFromFile(argAppInfoFile);
        } catch (JsonReader.FileLoadException ex) {
            System.err.println("Error reading <app-info-file>: " + ex.getMessage());
            System.exit(1); return;
        }

        // Run through Dropbox API authorization process
        DbxRequestConfig requestConfig = new DbxRequestConfig("examples-authorize");
        DbxWebAuth webAuth = new DbxWebAuth(requestConfig, appInfo);
        DbxWebAuth.Request webAuthRequest = DbxWebAuth.newRequestBuilder()
                .withNoRedirect()
                .build();

        String authorizeUrl = webAuth.authorize(webAuthRequest);
        System.out.println("1. Go to " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first).");
        System.out.println("3. Copy the authorization code.");
        System.out.print("Enter the authorization code here: ");

        String code = null;
        try {
            code = new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (code == null) {
            System.exit(1); return;
        }
        code = code.trim();

        DbxAuthFinish authFinish;
        try {
            authFinish = webAuth.finishFromCode(code);
        } catch (DbxException ex) {
            System.err.println("Error in DbxWebAuth.authorize: " + ex.getMessage());
            System.exit(1); return;
        }

        System.out.println("Authorization complete.");
        System.out.println("- User ID: " + authFinish.getUserId());
        System.out.println("- Account ID: " + authFinish.getAccountId());
        System.out.println("- Access Token: " + authFinish.getAccessToken());

        // Save auth information to output file.
        DbxAuthInfo authInfo = new DbxAuthInfo(authFinish.getAccessToken(), appInfo.getHost());
        File output = new File(argAuthFileOutput);
        try {
            DbxAuthInfo.Writer.writeToFile(authInfo, output);
            System.out.println("Saved authorization information to \"" + output.getCanonicalPath() + "\".");
        } catch (IOException ex) {
            System.err.println("Error saving to <auth-file-out>: " + ex.getMessage());
            System.err.println("Dumping to stderr instead:");
            DbxAuthInfo.Writer.writeToStream(authInfo, System.err);
            System.exit(1); return;
        }
    }
}
