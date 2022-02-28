package com.karold.swreportapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.karold.swreportapp.exception.TooManyResultsException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonApiResponse<T> {

    private int count;

    private String next;

    private String previous;

    private List<T> results;

    public Optional<T> getResultIfCountEqualsOne() {
        if (count == 1) {
            return Optional.of(results.get(0));
        } else if (count > 1) {
            throw new TooManyResultsException(results.get(0).getClass().getSimpleName());
        } else {
            return Optional.empty();
        }
    }

}
