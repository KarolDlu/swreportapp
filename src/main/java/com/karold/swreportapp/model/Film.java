package com.karold.swreportapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
