package org.cps.swimlane.venues.better.external;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class ResponseData<T> {


    private DataHolder<T> data;

    public List<T> getData() {
        return data.values;
    }

    /**
     * Returns keyed object
     *
     * {
     *     "data": {
     *         "49": {
     *         },
     *         "50": {
     *
     *         }
    *    }
     * }
     */
    private static class DataHolder<T> {

        @JsonIgnore
        private final List<T> values = new ArrayList<>();

        @JsonAnySetter
        public void set(String key, T value) {
            values.add(value);
        }
    }
}