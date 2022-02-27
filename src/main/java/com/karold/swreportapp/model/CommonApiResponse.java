package com.karold.swreportapp.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommonApiResponse<T> {

    private int count;

    private String next;

    private String previous;

    private List<T> results;

    @JsonUnwrapped
    private EntityInfo entityInfo;

    public T getResultIfCountEqualsOne() {
        if (count == 1) {
            return results.get(0);
        } else if (count > 1) {
            //TODO throw to many results
            return null;
        } else {
            //TODO throw planet not found
            return null;
        }
    }

}
