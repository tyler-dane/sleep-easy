package com.sleep.authorize;

import com.github.scribejava.core.builder.api.DefaultApi20;

//TODO submit PR to scribe java for this client. Remove your comments and paths
public class DropboxApi20 extends DefaultApi20 {

    public DropboxApi20() {}

    private static class InstanceHolder {
        private static final DropboxApi20 INSTANCE = new DropboxApi20();
    }

    public static DropboxApi20 instance() {
        return InstanceHolder.INSTANCE;
    }

    /** Base URL call to dropbox.com. Required parameters are appended to this String in
     * {@link com.sleep.authorize.provider.DropboxProvider}
     */
    public static final String BASE_AUTHORIZE_URL =
            //"https://www.dropbox.com/1/oauth2/authorize?client_id=%s&response_type=code&redirect_uri=%s";
            "https://www.dropbox.com/1/oauth2/authorize";

    public static final String ACCESS_TOKEN_ENDPOINT = "https://api.dropbox.com/1/oauth2/token";

    @Override
    public String getAccessTokenEndpoint() {
        return ACCESS_TOKEN_ENDPOINT;
    }

    @Override
    public String getAuthorizationBaseUrl() {
        return BASE_AUTHORIZE_URL;
    }
}
