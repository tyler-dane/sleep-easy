package com.sleep.authorize;


import com.sleep.authorize.provider.DropboxProvider;
import com.sleep.settings.AuthorizeSettings;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public class OAuthServer {

    public static void run() throws IOException {
        int port = AuthorizeSettings.HTTP_SERVER_PORT;

        HttpServer oAuthServer = HttpServer.create(
                new InetSocketAddress(port), 0);

        HttpContext context = oAuthServer.createContext("/");

        //TODO figure out how to get this to work again:         context.setHandler(OAuthServer::handleRequest);
        context.setHandler(exchange -> {
            try {
                handleRequest(exchange);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        oAuthServer.start();
        System.out.println("OAuth server running at: http://localhost:" + port + "/");
    }

    private static void handleRequest(HttpExchange exchange) throws IOException, ExecutionException, InterruptedException {
        String requestURIString = exchange.getRequestURI().toString();
        URI request = exchange.getRequestURI();
        String response = "requestURI = " + requestURIString;

        if (requestURIString.equals("/auth-dropbox")) {
            DropboxProvider.authorize(request, response);
        }

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
