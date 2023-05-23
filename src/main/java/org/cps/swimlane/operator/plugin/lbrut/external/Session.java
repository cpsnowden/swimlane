package org.cps.swimlane.operator.plugin.lbrut.external;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class Session {

    @JsonProperty("startTimeUTC")
    private Instant startTimeUTC;

    @JsonProperty("endTimeUTC")
    private Instant endTimeUTC;

    @JsonProperty("siteName")
    private String siteName;

    @JsonProperty("title")
    private String title;

    @JsonProperty("resourceName")
    private String resourceName;

    @JsonProperty("slotsTotal")
    private int slotsTotal;

    @JsonProperty("slotsAvailable")
    private int slotsAvailable;

    public Instant getStartTimeUTC() {
        return startTimeUTC;
    }

    public Instant getEndTimeUTC() {
        return endTimeUTC;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public int getSlotsTotal() {
        return slotsTotal;
    }

    public int getSlotsAvailable() {
        return slotsAvailable;
    }

    public String getTitle() {
        return title;
    }
}
