//package com.sleep.upload;
//
//import com.dropbox.core.*;
//import com.dropbox.core.json.JsonReader;
//import com.dropbox.core.v2.DbxClientV2;
//import org.mortbay.jetty.servlet.SessionHandler;
//
//
//import org.eclipse.jetty.server.Request;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.server.handler.AbstractHandler;
//import org.eclipse.jetty.server.session.SessionHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.io.PrintWriter;
//import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;
//
//
///**
// *
// * From: http://dropbox.github.io/dropbox-sdk-java/api-docs/v2.0.x/
// * TODO:
// * - make sure you're not using v1.8 ^^ **
// * - replace prints for logger
// */
//public class DbxWebAuthDemo extends AbstractHandler{
//
//    public void initServer(){
//
//        // Run server
//        try {
//
//            int port = 7070; //todo move to settings file
//
//            Server server = new Server(port);
//            org.eclipse.jetty.server.session.SessionHandler sessionHandler = new org.eclipse.jetty.server.session.SessionHandler()
//            sessionHandler.setServer(server);
//            sessionHandler.setHandler(main);
//            server.setHandler(sessionHandler);
//
//            server.start();
//            System.out.println("Server running: http://localhost:" + port + "/");
//
//            server.join();
//        } catch (Exception ex) {
//            System.err.println("Error running server: " + ex.getMessage());
//            System.exit(1);
//        }
//    }
//
//    public static void doStart(HttpServletRequest request, HttpServletResponse response)
//            throws IOException, ServletException, JsonReader.FileLoadException {
//
//        //Part 0: Setup
//        DbxRequestConfig requestConfig = new DbxRequestConfig("text-edit/0.1");
//        DbxAppInfo appInfo = DbxAppInfo.Reader.readFromFile("api.app");
//        DbxWebAuth auth = new DbxWebAuth(requestConfig, appInfo);
//
//        String redirectUri = "http://my-server.com/dropbox-auth-finish";
//
//        // Select a spot in the session for DbxWebAuth to store the CSRF token.
//        HttpSession session = request.getSession(true);
//        String sessionKey = "dropbox-auth-csrf-token";
//        DbxSessionStore csrfTokenStore = new DbxStandardSessionStore(session, sessionKey);
//
//        // Build an auth request
//        DbxWebAuth.Request authRequest = DbxWebAuth.newRequestBuilder()
//                .withRedirectUri(redirectUri, csrfTokenStore)
//                .build();
//
//        // Start authorization.
//        String authorizePageUrl = auth.authorize(authRequest);
//
//        // Redirect the user to the Dropbox website so they can approve our application.
//        // The Dropbox website will send them back to "http://my-server.com/dropbox-auth-finish"
//        // when they're done.
//        response.sendRedirect(authorizePageUrl);
//
//        System.out.println("*** done");
//    }
//
//
//    public void setup () throws JsonReader.FileLoadException, IOException {
//
//        //Part 0: Setup
//        DbxRequestConfig requestConfig = new DbxRequestConfig("text-edit/0.1");
//        DbxAppInfo appInfo = DbxAppInfo.Reader.readFromFile("api.app");
//        DbxWebAuth auth = new DbxWebAuth(requestConfig, appInfo);
//
//        String redirectUri = "http://my-server.com/dropbox-auth-finish";
//
//        // Part 1: Handler for "http://my-server.com/dropbox-auth-start"
//        HttpServletRequest request = ...
//        HttpServletResponse response = ...
//
//        // Select a spot in the session for DbxWebAuth to store the CSRF token.
//        HttpSession session = request.getSession(true);
//        String sessionKey = "dropbox-auth-csrf-token";
//        DbxSessionStore csrfTokenStore = new DbxStandardSessionStore(session, sessionKey);
//
//        // Build an auth request
//        DbxWebAuth.Request authRequest = DbxWebAuth.newRequestBuilder()
//                .withRedirectUri(redirectUri, csrfTokenStore)
//                .build();
//
//        // Start authorization.
//        String authorizePageUrl = auth.authorize(authRequest);
//
//        // Redirect the user to the Dropbox website so they can approve our application.
//        // The Dropbox website will send them back to "http://my-server.com/dropbox-auth-finish"
//        // when they're done.
//        response.sendRedirect(authorizePageUrl);
//
//        // Part 2: Handler for "http://my-server.com/dropbox-auth-finish":
//        HttpServletRequest finishRequest = ...
//        HttpServletResponse finishResponse = ...
//
//        // Fetch the session to verify our CSRF token
//        HttpSession finishSession = finishRequest.getSession(true);
//        String finishSessionKey = "dropbox-auth-csrf-token";
//        DbxSessionStore finishCsrfTokenStore = new DbxStandardSessionStore(finishSession, sessionKey);
//        String finishRedirectUri = "http://my-server.com/dropbox-auth-finish";
//
//        DbxAuthFinish authFinish;
//        try {
//            authFinish = auth.finishFromRedirect(finishRedirectUri, finishCsrfTokenStore, finishRequest.getParameterMap());
//        } catch (DbxWebAuth.BadRequestException ex) {
//            System.out.println("On /dropbox-auth-finish: Bad request: " + ex.getMessage());
//            finishResponse.sendError(400);
//            return;
//        } catch (DbxWebAuth.BadStateException ex) {
//            // Send them back to the start of the auth flow.
//            finishResponse.sendRedirect("http://my-server.com/dropbox-auth-start");
//            return;
//        } catch (DbxWebAuth.CsrfException ex) {
//            System.out.println("On /dropbox-auth-finish: CSRF mismatch: " + ex.getMessage());
//            finishResponse.sendError(403, "Forbidden.");
//            return;
//        } catch (DbxWebAuth.NotApprovedException ex) {
//            // When Dropbox asked "Do you want to allow this app to access your
//            // Dropbox account?", the user clicked "No".
//            //...
//            return;
//        } catch (DbxWebAuth.ProviderException ex) {
//            System.out.println("On /dropbox-auth-finish: Auth failed: " + ex.getMessage());
//            finishResponse.sendError(503, "Error communicating with Dropbox.");
//            return;
//        } catch (DbxException ex) {
//            System.out.println("On /dropbox-auth-finish: Error getting token: " + ex.getMessage());
//            finishResponse.sendError(503, "Error communicating with Dropbox.");
//            return;
//        }
//        String accessToken = authFinish.getAccessToken();
//
//        // Save the access token somewhere (probably in your database) so you
//        // don't need to send the user through the authorization process again.
//        //...
//
//        // Now use the access token to make Dropbox API calls.
//        DbxClientV2 client = new DbxClientV2(requestConfig, accessToken);
//    }
//}
