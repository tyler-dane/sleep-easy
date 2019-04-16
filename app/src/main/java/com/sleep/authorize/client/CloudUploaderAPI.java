package com.sleep.authorize.client;

import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;

//todo move this to the 'upload' package (?)
public interface CloudUploaderAPI {
    //not sure if type should be String or Response
    Token uploadUserFiles(Response response) throws Exception;
}
