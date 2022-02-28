package com.karold.swreportapp.model.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReportItem {

    @JsonProperty("film_id")
    private String filmId;

    @JsonProperty("film_name")
    private String filmName;

    @JsonProperty("character_id")
    private String characterId;

    @JsonProperty("character_name")
    private String characterName;

    @JsonProperty("planet_id")
    private String planetId;

    @JsonProperty("planet_name")
    private String planetName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportItem that = (ReportItem) o;
        return Objects.equals(filmId, that.filmId) && Objects.equals(filmName, that.filmName) && Objects.equals(characterId, that.characterId) && Objects.equals(characterName, that.characterName) && Objects.equals(planetId, that.planetId) && Objects.equals(planetName, that.planetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, filmName, characterId, characterName, planetId, planetName);
    }
}
