package com.karold.swreportapp.service;

import com.karold.swreportapp.exception.SwApiRequestException;

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

    public String get(String resource, String params) {
        try {
            return httpClient.send(HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + resource + "?" + params))
                    .GET()
                    .build(),
                    HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception e) {
            throw new SwApiRequestException(e.getMessage(), e);
        }
    }

    public String getWithCustomUrl(String uri) {
        try {
            return httpClient.send(HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build(), HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception e) {
            throw new SwApiRequestException(e.getMessage(), e);
        }
    }

}
