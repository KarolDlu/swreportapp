package com.karold.swreportapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Planet {

    private String name;

    @JsonProperty("rotation_period")
    private String rotationPeriod;

    @JsonProperty("orbital_period")
    private String orbitalPeriod;

    private String diameter;

    private String climate;

    private String gravity;

    private String terrain;

    @JsonProperty("surface_water")
    private String surfaceWater;

    private String population;

    private List<String> residents;

    private List<String> films;

    @JsonUnwrapped
    private EntityInfo entityInfo;

}
