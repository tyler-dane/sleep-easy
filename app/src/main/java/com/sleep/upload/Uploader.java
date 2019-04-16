package com.sleep.upload;

import com.sleep.authorize.OAuthServer;

import java.io.IOException;


public class Uploader {

    public static void run() throws IOException {

        // Authenticate to remote storage provider
        OAuthServer.run();

        // TODO Upload backup
    }
}
