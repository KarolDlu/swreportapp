package com.karold.swreportapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    private String name;

    private String homeworld;

    private List<String> films;

    private String url;

    public String getId() {
        String[] parts = url.split("/");
        if (parts.length >= 6) {
            return parts[5];
        }
        //TODO throw cant get id from url
        return null;
    }

    public boolean checkIfComeFrom(String planet) {
        if (homeworld != null) {
            return homeworld.equals(planet);
        }
        return false;
    }

}
