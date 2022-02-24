package com.karold.swreportapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonApiResponse<T> {

    int count;

    String next;

    String previous;

    List<T> results;

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
