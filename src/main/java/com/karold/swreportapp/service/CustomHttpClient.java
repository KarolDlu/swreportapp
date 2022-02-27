package com.karold.swreportapp.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CustomHttpClient {

    private final HttpClient httpClient;
    private final String baseUrl;

    public CustomHttpClient(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        this.baseUrl = baseUrl;
    }

    public HttpResponse<String> get(String resource, String params) throws IOException, InterruptedException { //TODO change endpoint to resource
        return httpClient.send(HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + resource + "?" + params))
                .GET()
                .build(), HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> getWithCustomUrl(String uri) throws IOException, InterruptedException {
        return httpClient.send(HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build(), HttpResponse.BodyHandlers.ofString());
    }

}
