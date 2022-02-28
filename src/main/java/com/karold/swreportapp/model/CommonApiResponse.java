package com.karold.swreportapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.karold.swreportapp.exception.ResultsCountNotEqualsOneException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonApiResponse<T> {

    private int count;

    private String next;

    private List<T> results;

    public T getResultIfCountEqualsOne() {
        if (count == 1) {
            return results.get(0);
        } else {
            throw new ResultsCountNotEqualsOneException();
        }
    }
}
