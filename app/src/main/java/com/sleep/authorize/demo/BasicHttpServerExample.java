package com.sleep.authorize.demo;

import com.sleep.settings.AuthorizeSettings;
import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public class BasicHttpServerExample {

    public static void run() throws IOException {
        int port = AuthorizeSettings.HTTP_SERVER_PORT;

        HttpServer server = HttpServer.create(
                new InetSocketAddress(port), 0);
        HttpContext context = server.createContext("/");
        context.setHandler(BasicHttpServerExample::handleRequest);
        server.start();
        System.out.println("Server running at: http://localhost:" + port + "/");
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        String requestURI = exchange.getRequestURI().toString();

        if (requestURI.equals("/hi")) {
            System.out.println("you sent 'hi");
        }

        System.out.println("requestURI:" + requestURI);

        printRequestInfo(exchange);
        String response = "This is the response at " + requestURI;
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void printRequestInfo(HttpExchange exchange) {
        System.out.println("-- headers --");
        Headers requestHeaders = exchange.getRequestHeaders();
        requestHeaders.entrySet().forEach(System.out::println);

        System.out.println("-- body --");
        InputStream requestBody = exchange.getRequestBody();
        System.out.println(requestBody);

        System.out.println("-- principle --");
        HttpPrincipal principal = exchange.getPrincipal();
        System.out.println(principal);

        System.out.println("-- HTTP method --");
        String requestMethod = exchange.getRequestMethod();
        System.out.println(requestMethod);

        System.out.println("-- query --");
        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();
        System.out.println(query);
    }

    public void registerServletHandler(){

    }


    private static void stopServer() {
        System.out.println("TODO");
    }
}
