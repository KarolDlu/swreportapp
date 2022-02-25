package com.karold.swreportapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

import java.util.List;

@Getter
public class Film {

    private String title;

    @JsonProperty("episode_id")
    private int episodeId;

    @JsonProperty("opening_crawl")
    private String openingCrawl;

    private String director;

    private String producer;

    @JsonProperty("release_date")
    private String releaseDate;

    private List<String> characters;

    private List<String> planets;

    private List<String> starships;

    private List<String> vehicles;

    private List<String> species;

    @JsonUnwrapped
    private EntityInfo entityInfo;

    public String getId(){return entityInfo.getIdFromUrl();}

    public String getUrl() {
        return entityInfo.getUrl();
    }

}
