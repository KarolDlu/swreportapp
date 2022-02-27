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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SWApiService {

    private static final String PERSON_RESOURCE = "/people/";
    private static final String PLANET_RESOURCE = "/planets/";

    private final CustomHttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public SWApiService(CustomHttpClient httpClient) {
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();
    }

    public Film getFilm(String url) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.getWithCustomUrl(url);
        return objectMapper.readValue(response.body(), Film.class);

    }

    public Planet getPlanetByName(String name) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.get(PLANET_RESOURCE, "search=" + name);
        CommonApiResponse<Planet> result = objectMapper.readValue(response.body(), new TypeReference<>() {
        });
        return result.getResultIfCountEqualsOne();
    }

    public List<Person> searchPersonByName(String phrase) throws IOException, InterruptedException {
        return getAllPersonsByPhrase("search=" + phrase);
    }


    public List<Person> searchPersonByPhraseAndHomeWorld(String phrase, String planetUrl) throws IOException, InterruptedException {
        List<Person> personList = searchPersonByName(phrase);
        return personList.stream()
                .filter(person -> person.checkIfComeFrom(planetUrl))
                .collect(Collectors.toList());
    }

    private List<Person> getAllPersonsByPhrase(String params) throws IOException, InterruptedException {

        HttpResponse<String> response = httpClient.get(PERSON_RESOURCE, params);
        CommonApiResponse<LinkedHashMap<String, String>> responseBody = objectMapper.readValue(response.body(), new TypeReference<>() {
        });
        List<LinkedHashMap<String, String>> results = new ArrayList<>(responseBody.getResults());

        while (responseBody.getNext() != null) {
            response = httpClient.getWithCustomUrl(responseBody.getNext());
            responseBody = objectMapper.readValue(response.body(), new TypeReference<>() {
            });
            results.addAll(responseBody.getResults());
        }
        return objectMapper.convertValue(results, new TypeReference<>() {
        });
    }

}
