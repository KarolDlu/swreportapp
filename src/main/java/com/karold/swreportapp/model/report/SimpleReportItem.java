package com.karold.swreportapp.model.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class SimpleReportItem implements ReportItem {

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

}
