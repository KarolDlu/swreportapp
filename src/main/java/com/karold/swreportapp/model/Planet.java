package com.karold.swreportapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Planet {

    private String name;

    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return Objects.equals(name, planet.name) && Objects.equals(url, planet.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    public String getId() {
        String[] parts = url.split("/");
        if (parts.length >= 6) {
            return parts[5];
        }
        //TODO throw cant get id from url
        return null;
    }

}
