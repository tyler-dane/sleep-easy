package com.sleep.authorize.provider;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.utils.OAuthEncoder;
import com.sleep.authorize.DropboxApi20;
import com.sleep.authorize.handler.DropboxHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;


public class DropboxProvider {

    /**
     * DropBox's Auth FLOW:
     * STEP 1: Begin Auth
     *      - Direct user to authURL; save 'code' as the response to the auth URL
     * STEP 2: Convert AuthURL to AccessToken
     *      - Call the `/token` endpoint
     *      - curl https://api.dropbox.com/1/oauth2/token -d code=<authorization code> -d grant_type=authorization_code -d redirect_uri=<redirect URI> -u <app key>:<app secret>
     * STEP 3: Call the API using the AccessToken header
     *
     *
     *https://blogs.dropbox.com/developers/2013/07/using-oauth-2-0-with-the-core-api/
     */
    public static void authorize(URI request, String response) throws InterruptedException, ExecutionException,
            IOException {

        final Scanner in = new Scanner(System.in, "UTF-8");

        // Build AuthService
        //TODO find way to eliminate hard coding id and secret or make it easier for non-technical user to use
        String clientId = "secret";
        String apiSecret = "supersecret";
        String redirectUri = "http://localhost:8500/authorize";

        OAuth20Service oAuth20Service = new ServiceBuilder(clientId)
                .apiSecret(apiSecret)
                .callback(redirectUri)
                .build(DropboxApi20.instance());

        // Direct user to AuthUrl
        String authUrl = oAuth20Service.getAuthorizationUrl();
        System.out.println("Build oAuth service. Now go to Authorization URL:");
        System.out.println(OAuthEncoder.decode(authUrl));
        System.out.println("And paste the authorization code here/in the SleepEasy code");

        //TODO implement
        // After authorizing the app, you'll see the code in the URL:
            // http://localhost:8500/authorize?code=Qo5Naasldfhasldfuahlkj
        // You need to implement methods to strip out only the code part
        // This is how you can limit some of the hard-coding/user interaction
        final String code =  DropboxHandler.doGet('code param');

        // Get access token
        System.out.println("Getting access token ...");
        final OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
//        System.out.println("Got AccessToken!");
//        System.out.println("(The raw response looks like this: " + accessToken.getRawResponse() + "')");


        //TODO get request token
        //TODO get access token
        //TODO sign request
    }

    public OAuth2AccessTokenJsonExtractor getAccessTokenExtractor() {
        return OAuth2AccessTokenJsonExtractor.instance();
    }

    public Verb getAccessTokenVerb(){
        return Verb.POST;
    }
}
