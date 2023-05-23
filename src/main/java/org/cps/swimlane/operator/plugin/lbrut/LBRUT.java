package org.cps.swimlane.operator.plugin.lbrut;

import jakarta.enterprise.context.ApplicationScoped;
import org.cps.swimlane.model.LaneAvailability;
import org.cps.swimlane.model.Venue;
import org.cps.swimlane.operator.core.PoolVenueOperator;
import org.cps.swimlane.operator.plugin.lbrut.external.LBRUTApi;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LBRUT implements PoolVenueOperator {

    public static final String OPERATOR_NAME = "lbrut";

    @ConfigProperty(name = "lbrut.api-authentication-key")
    String apiAuthenticationKey;

    @RestClient
    LBRUTApi lbrutApi;

    @Override
    public List<LaneAvailability> getAvailability(String venueSlug, LocalDate date) {

        Instant start = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant end = date.atTime(23, 59, 59).toInstant(ZoneOffset.UTC);
        return lbrutApi.sessions(apiAuthenticationKey, venueSlug)
                .getSessions()
                .stream()
                .filter(session -> session.getStartTimeUTC().isBefore(end) && session.getStartTimeUTC().isAfter(start))
                .map(session -> {
                    OffsetDateTime startTime = session.getStartTimeUTC().atOffset(ZoneOffset.UTC);
                    return new LaneAvailability(
                            startTime.toLocalDate(),
                            session.getStartTimeUTC(),
                            session.getEndTimeUTC(),
                            OPERATOR_NAME,
                            session.getSiteName(),
                            session.getTitle() + " " + session.getResourceName(),
                            session.getSlotsAvailable()
                    );
                }).collect(Collectors.toList());
    }

    @Override
    public List<Venue> getVenues() {
        //Expand once know how to correlate venues with swimming activity id
        return Collections.singletonList(new Venue("Pools On The Park", "PDD12,PDD13,PDD14,PDD09,PDD10,PDD11", OPERATOR_NAME));
    }

    @Override
    public String getOperatorId() {
        return OPERATOR_NAME;
    }
}
