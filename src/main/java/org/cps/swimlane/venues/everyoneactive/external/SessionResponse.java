package org.cps.swimlane.venues.everyoneactive.external;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SessionResponse {

    @JsonProperty("sessions")
    private List<Session> sessions;

    public List<Session> getSessions() {
        return sessions;
    }
}
