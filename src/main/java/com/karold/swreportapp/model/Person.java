package com.karold.swreportapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(homeworld, person.homeworld) && Objects.equals(films, person.films) && Objects.equals(url, person.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, homeworld, films, url);
    }

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
