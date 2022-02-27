package com.karold.swreportapp.model.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@JsonPropertyOrder({
        "report_id",
        "query_criteria_character_phrase",
        "query_criteria_planet_name",
        "result"
})
@Getter
@ToString
@NoArgsConstructor
public class Report {

    @Id
    @JsonProperty("report_id")
    private Long id;

    @JsonProperty("query_criteria_character_phrase")
    private String characterPhrase;

    @JsonProperty("query_criteria_planet_name")
    private String planetName;

    @ElementCollection
    private List<ReportItem> result;

    public Report(Long id, String characterPhrase, String planetName, List<ReportItem> result) {
        this.id = id;
        this.characterPhrase = characterPhrase;
        this.planetName = planetName;
        this.result = result;
    }
}
