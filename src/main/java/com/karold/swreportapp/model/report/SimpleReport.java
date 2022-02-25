package com.karold.swreportapp.model.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@JsonPropertyOrder({
        "report_id",
        "query_criteria_character_phrase",
        "query_criteria_planet_name",
        "result"
})
@Getter
@ToString
public class SimpleReport implements Report {

    @JsonProperty("report_id")
    private Long id;

    @JsonProperty("query_criteria_character_phrase")
    private String characterPhrase;

    @JsonProperty("query_criteria_planet_name")
    private String planetName;

    private List<SimpleReportItem> result;

    public SimpleReport(String characterPhrase, String planetName, List<SimpleReportItem> result) {
        this.characterPhrase = characterPhrase;
        this.planetName = planetName;
        this.result = result;
    }
}
