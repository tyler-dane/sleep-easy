package com.sleep.authorize.provider;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.oauth.OAuthService;
import com.github.scribejava.core.builder.api.BaseApi;
import com.sleep.authorize.PropertiesHolder;

//TODO not sure if I should use DefaultApi20 or BaseAPI
public class OAuthServiceProvider {
    public static OAuthService getInstance(String appName, Class<? extends DefaultApi20> providerClass){
        String consumerKey = PropertiesHolder.getProperty(String.format("%s.consumerkey", appName));
        String consumerSecret = PropertiesHolder.getProperty(String.format("%s.consumersecret", appName));
        String callbackUrl = String.format(PropertiesHolder.getProperty("appurl") + "/%s-callback", appName);

        ServiceBuilder serviceBuilder = new ServiceBuilder(consumerKey);
        serviceBuilder.apiKey(consumerKey);
        serviceBuilder.apiSecret(consumerSecret);
        serviceBuilder.callback(callbackUrl);
        serviceBuilder.build(api);

        return serviceBuilder;

        return new ServiceBuilder
                .provider(providerClass)
                .apiKey(consumerKey)
                .apiSecret(consumerSecret)
                .callback(callbackUrl)
                .build();
    }
}
