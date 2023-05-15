package org.cps.swimlane.operator.plugin.better.external;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public class FormatTime {

    @JsonProperty("format_24_hour")
    private LocalTime format24Hour;

    public LocalTime getFormat24Hour() {
        return format24Hour;
    }
}
