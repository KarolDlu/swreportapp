package com.karold.swreportapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karold.swreportapp.model.CommonApiResponse;
import com.karold.swreportapp.model.Film;
import com.karold.swreportapp.model.Person;
import com.karold.swreportapp.model.Planet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class SWApiService {

    private CustomHttpClient httpClient;
    private ObjectMapper objectMapper;

    @Autowired
    public SWApiService(CustomHttpClient httpClient) {
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();
    }

    public Film getFilmById(String filmId) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.get(filmUrl(filmId));
        return objectMapper.readValue(response.body(), Film.class);
    }

    public Planet getPlanetById(String planetId) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.get("/planets/" + planetId + "/");
        return objectMapper.readValue(response.body(), Planet.class);
    }

    public Person getPersonById(String personId) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.get("/people/" + personId + "/");
        return objectMapper.readValue(response.body(), Person.class);
    }

    public Planet searchPlanetByName(String name) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.get("/planets/", "search=" + name);
        CommonApiResponse<Planet> result = objectMapper.readValue(response.body(), new TypeReference<>() {
        });
        //TODO zamień Blabla na planet
        return result.getResultIfCountEqualsOne();
    }


    public List<Person> searchPersonByPhrase(String phrase) throws IOException, InterruptedException {
        return getAllResults(Person.class, "/people/", "search=" + phrase);
    }

    private <T> List<T> getAllResults(Class<T> resultType, String endpoint, String params) throws IOException, InterruptedException {

        ArrayList<T> results = new ArrayList<>();

        HttpResponse<String> response = httpClient.get(endpoint, params);
        CommonApiResponse<T> responseBody = objectMapper.readValue(response.body(), new TypeReference<>() {});
        results.addAll(responseBody.getResults());

        while (responseBody.getNext() != null) {
            response = httpClient.getWithCustomUrl(responseBody.getNext());
            responseBody = objectMapper.readValue(response.body(), new TypeReference<>() {
            });
            results.addAll(responseBody.getResults());
        }
        return results;
    }

    private String filmsUrl() {
        return "/films/";
    }

    private String filmUrl(String id) {
        return filmsUrl() + id + "/";
    }

}
