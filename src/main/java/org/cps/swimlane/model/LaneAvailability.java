package org.cps.swimlane.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LaneAvailability {

    private final LocalDate date;
    private final LocalDateTime startAtUTC;
    private final LocalDateTime endAtUTC;

    private final String operator;
    private final String venue;
    private final String laneName;
    private final int spaces;

    public LaneAvailability(LocalDate date,
                            LocalDateTime startAtUTC,
                            LocalDateTime endAtUTC,
                            String operator,
                            String venue,
                            String laneName,
                            int spaces) {
        this.date = date;
        this.startAtUTC = startAtUTC;
        this.endAtUTC = endAtUTC;
        this.operator = operator;
        this.venue = venue;
        this.laneName = laneName;
        this.spaces = spaces;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDateTime getStartAtUTC() {
        return startAtUTC;
    }

    public LocalDateTime getEndAtUTC() {
        return endAtUTC;
    }

    public String getOperator() {
        return operator;
    }

    public String getVenue() {
        return venue;
    }

    public String getLaneName() {
        return laneName;
    }

    public int getSpaces() {
        return spaces;
    }
}
