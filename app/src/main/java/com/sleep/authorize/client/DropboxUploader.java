package com.sleep.authorize.client;

import com.dropbox.core.DbxRequestConfig;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.google.api.client.json.JsonParser;
import com.google.gson.JsonObject;

import com.sleep.authorize.handler.DropboxHandler;

import java.util.Locale;

public class DropboxUploader implements CloudUploaderAPI {

    @Override
    public Token uploadUserFiles(Response response) throws Exception {
        Token accessToken = DropboxHandler.DROP_BOX_PROVIDER.getAccessTokenExtractor().extract(response);
        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());
        DbxClient client = new DbxClient(config, accessToken.getToken());

        //TODO implement
        //File backupZip = ReferenceToPreviouslyZippedFile;
        JsonObject rawResponseJson = new JsonParser().parse(accessToken.getRawResponse()).getAsJsonObject();
        String clientUid = rawResponseJson.get("uid").getAsString(); //needed to not overwrite if clients have same files
        visitFolder(client, "/", clientUid, downloadedFiles);


        return accessToken;
    }
}
