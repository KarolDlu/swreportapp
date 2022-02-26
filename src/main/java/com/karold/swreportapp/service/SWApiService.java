package com.karold.swreportapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karold.swreportapp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SWApiService {

    private static final String PERSON_ENDPOINT = "/people/";
    private static final String FILM_ENDPOINT = "/films/";
    private static final String PLANET_ENDPOINT = "/planets/";

    private CustomHttpClient httpClient;
    private ObjectMapper objectMapper;

    @Autowired
    public SWApiService(CustomHttpClient httpClient) {
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();
    }

    public Film getFilm(String url) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.getWithCustomUrl(url);
        return objectMapper.readValue(response.body(), Film.class);

    }

    public Film getFilmById(String filmId) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.get(FILM_ENDPOINT + filmId + "/");
        return objectMapper.readValue(response.body(), Film.class);
    }

    public Planet getPlanetById(String planetId) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.get(PLANET_ENDPOINT + planetId + "/");
        return objectMapper.readValue(response.body(), Planet.class);
    }

    public Person getPersonById(String personId) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.get(PERSON_ENDPOINT + personId + "/");
        return objectMapper.readValue(response.body(), Person.class);
    }

    public Planet getPlanetByName(String name) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.get(PLANET_ENDPOINT, "search=" + name);
        CommonApiResponse<Planet> result = objectMapper.readValue(response.body(), new TypeReference<>() {
        });
        return result.getResultIfCountEqualsOne();
    }

    public List<Person> searchPersonByName(String phrase) throws IOException, InterruptedException {
        return getAllResults(ResultsType.PERSON, PERSON_ENDPOINT, "search=" + phrase);
    }


    public List<Person> searchPersonByPhraseAndHomeWorld(String phrase, String planetUrl) throws IOException, InterruptedException {
        List<Person> personList = searchPersonByName(phrase);
        return personList.stream().filter(person -> person.checkIfComeFrom(planetUrl)).collect(Collectors.toList());
    }

    private List getAllResults(ResultsType resultsType, String endpoint, String params) throws IOException, InterruptedException {
        ArrayList<LinkedHashMap> results = new ArrayList<>();

        HttpResponse<String> response = httpClient.get(endpoint, params);
        CommonApiResponse<LinkedHashMap> responseBody = objectMapper.readValue(response.body(), new TypeReference<>() {
        });
        results.addAll(responseBody.getResults());

        while (responseBody.getNext() != null) {
            response = httpClient.getWithCustomUrl(responseBody.getNext());
            responseBody = objectMapper.readValue(response.body(), new TypeReference<>() {
            });
            results.addAll(responseBody.getResults());
        }
        return convertToResultType(ResultsType.PERSON, results);
    }

    private List convertToResultType(ResultsType resultsType, List<LinkedHashMap> results) {
        List convertedResults = new ArrayList();
        switch (resultsType) {
            case PERSON:
                convertedResults = objectMapper.convertValue(results, new TypeReference<ArrayList<Person>>() {
                });
                break;
            case FILM:
                convertedResults = objectMapper.convertValue(results, new TypeReference<ArrayList<Film>>() {
                });
                break;
            case PLANET:
                convertedResults = objectMapper.convertValue(results, new TypeReference<ArrayList<Planet>>() {
                });
                break;
        }
        return convertedResults;

    }

    private String filmsUrl() {
        return "/films/";
    }

    private String filmUrl(String id) {
        return filmsUrl() + id + "/";
    }

}
