package com.karold.swreportapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Person {

    private String name;

    private String height;

    private String mass;

    @JsonProperty("hair_color")
    private String hairColor;

    @JsonProperty("skin_color")
    private String skinColor;

    @JsonProperty("eye_color")
    private String eyeColor;

    @JsonProperty("birth_year")
    private String birthYear;

    private String gender;

    private String homeworld;

    private List<String> films;

    private List<String> species;

    private List<String> vehicles;

    private List<String> starships;

    @JsonUnwrapped
    private EntityInfo entityInfo;

    public String getId(){return entityInfo.getIdFromUrl();}

    public String getUrl() {
        return entityInfo.getUrl();
    }

    public boolean checkIfComeFrom(String planet) {
        if (homeworld != null) {
            return homeworld.equals(planet);
        }
        return false;
    }

}
