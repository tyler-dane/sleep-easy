package com.sleep.authorize.handler;

import com.github.scribejava.core.model.Token;

public interface OAuthHandler {
    Token EMPTY_TOKEN = null;
    String CODE_PARAM = "code"; //TODO move this to Settings file

    //TODO add method to register Servlet, like in `multisite-oauth:OAuthHandler`
}
