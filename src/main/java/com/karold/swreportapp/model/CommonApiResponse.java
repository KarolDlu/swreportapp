package com.karold.swreportapp.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CommonApiResponse<T> {

    private int count;

    private String next;

    private String previous;

    private List<T> results;

    @JsonUnwrapped
    private EntityInfo entityInfo;

    public CommonApiResponse(int count, String next, String previous, List<T> results, EntityInfo entityInfo) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
        this.entityInfo = entityInfo;
    }

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
