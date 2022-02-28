package com.karold.swreportapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karold.swreportapp.exception.JsonToObjectMappingException;
import com.karold.swreportapp.exception.ResourceNotFoundException;
import com.karold.swreportapp.model.CommonApiResponse;
import com.karold.swreportapp.model.Film;
import com.karold.swreportapp.model.Person;
import com.karold.swreportapp.model.Planet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Film getFilm(String url) {
        String response = httpClient.getWithCustomUrl(url);
        try {
            return objectMapper.readValue(response, Film.class);
        } catch (Exception e) {
            throw new JsonToObjectMappingException(response, CommonApiResponse.class);
        }
    }

    public Planet getPlanetByName(String name) {
        String response = httpClient.get(PLANET_RESOURCE, "search=" + name);
        CommonApiResponse<Planet> result;
        try {
            result = objectMapper.readValue(response, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new JsonToObjectMappingException(response, CommonApiResponse.class);
        }
        return result.getResultIfCountEqualsOne().orElseThrow(() -> new ResourceNotFoundException("Planet", name));
    }


    public List<Person> searchPersonByPhraseAndHomeWorld(String phrase, String planetUrl) {
        List<Person> personList = searchPersonByName(phrase);
        return personList.stream()
                .filter(person -> person.checkIfComeFrom(planetUrl))
                .collect(Collectors.toList());
    }

    private List<Person> searchPersonByName(String phrase) {
        return getAllPersonsByPhrase("search=" + phrase);
    }

    private List<Person> getAllPersonsByPhrase(String params) {

        String response = httpClient.get(PERSON_RESOURCE, params);
        CommonApiResponse<LinkedHashMap<String, Object>> responseBody;
        try {
            responseBody = objectMapper.readValue(response, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new JsonToObjectMappingException(response, CommonApiResponse.class);
        }
        List<LinkedHashMap<String, Object>> results = new ArrayList<>(responseBody.getResults());

        while (responseBody.getNext() != null) {
            response = httpClient.getWithCustomUrl(responseBody.getNext());
            try {
                responseBody = objectMapper.readValue(response, new TypeReference<>() {
                });
            } catch (Exception e) {
                throw new JsonToObjectMappingException(response, CommonApiResponse.class);
            }
            results.addAll(responseBody.getResults());
        }
        return objectMapper.convertValue(results, new TypeReference<>() {
        });
    }

}
