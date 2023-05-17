package org.cps.swimlane.operator.plugin.better.external;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActivityResponse {

    @JsonDeserialize(using = DataDeserializer.class)
    private List<Activity> data;

    public List<Activity> getData() {
        return data;
    }

    /**
     * Odd response from better where some dates return an array and others a keyed object
     */
    public static class DataDeserializer extends JsonDeserializer<List<Activity>> {
        @Override
        public List<Activity> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            if (p.currentToken().equals(JsonToken.START_ARRAY)) {
                return ctxt.readValue(p, ctxt.getTypeFactory().constructCollectionLikeType(List.class, Activity.class));
            }
            DataHolder<Activity> holder = ctxt.readValue(p, ctxt.getTypeFactory().constructParametricType(DataHolder.class, Activity.class));
            return holder.values;
        }
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
