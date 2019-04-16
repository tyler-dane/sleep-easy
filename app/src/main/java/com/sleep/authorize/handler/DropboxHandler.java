package com.sleep.authorize.handler;

import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;
import com.google.common.base.Strings;
import com.sleep.authorize.client.DropboxUploader;
import com.sleep.authorize.provider.DropboxProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.sleep.authorize.provider.OAuthServiceProvider;

/** See multi-site-oauth:DropboxOAuthHandler */
public class DropboxHandler implements OAuthHandler {
    public static final String APP_NAME = "dropbox";
    public static final DropboxProvider DROP_BOX_PROVIDER = new DropboxProvider();

    private OAuth20Service service;


    private class SigninServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            service = OAuthServiceProvider.getInstance(APP_NAME, DROP_BOX_PROVIDER.getClass());
        }
    }


    private class CallbackServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            System.out.println("hi");
            String verifierParam = req.getParameter(CODE_PARAM);
            if (Strings.isNullOrEmpty(verifierParam)) {
                resp.getWriter().println("Failed to login to user account");
                return;
            }

            service = new DropboxProvider;
            try {
                Token accessToken = service.getAccessToken("SomeToken");
                //Token token = new DropboxUploader();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
        }
    }
}

