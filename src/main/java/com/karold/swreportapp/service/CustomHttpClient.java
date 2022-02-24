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

    public HttpResponse<String> get(String endpoint) throws IOException, InterruptedException {
        return get(endpoint, "");
    }

    public HttpResponse<String> get(String endpoint, String params) throws IOException, InterruptedException {
        return httpClient.send(HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + endpoint + "?" + params))
                .GET()
                .build(), HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> getWithCustomUrl(String url) throws IOException, InterruptedException {
        return httpClient.send(HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build(), HttpResponse.BodyHandlers.ofString());
    }

}
