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
public class Film {

    private String title;

    private String url;

    public String getId() {
        String[] parts = url.split("/");
        if (parts.length >= 6) {
            return parts[5];
        }
        //TODO throw cant get id from url
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(title, film.title) && Objects.equals(url, film.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }
}
