package org.cps.swimlane.venues.everyoneactive;

import jakarta.enterprise.context.ApplicationScoped;
import org.cps.swimlane.model.LaneAvailability;
import org.cps.swimlane.model.Venue;
import org.cps.swimlane.venues.operator.PoolVenueOperator;
import org.cps.swimlane.venues.everyoneactive.external.EveryoneActiveApi;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EveryoneActive implements PoolVenueOperator {

    public static final String OPERATOR_NAME = "everyone-active";

    @ConfigProperty(name = "everyone-active.api-authentication-key")
    String apiAuthenticationKey;

    @RestClient
    EveryoneActiveApi everyoneActiveApi;

    @Override
    public List<LaneAvailability> getAvailability(String venueSlug, LocalDate date) {

        long fromUTC = date.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
        long toUTC = date.atTime(23, 59, 59).toEpochSecond(ZoneOffset.UTC);
        return everyoneActiveApi.sessions(apiAuthenticationKey, venueSlug, fromUTC, toUTC)
                .getSessions()
                .stream()
                .map(session -> {
                    OffsetDateTime startTime = session.getStartTimeUTC().atOffset(ZoneOffset.UTC);
                    return new LaneAvailability(
                            startTime.toLocalDate(),
                            startTime.toLocalDateTime(),
                            session.getEndTimeUTC().atOffset(ZoneOffset.UTC).toLocalDateTime(),
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
        return Collections.singletonList(new Venue("Queen Mother Sports Centre", "155POOLPR1", OPERATOR_NAME));
    }

    @Override
    public String getOperatorName() {
        return OPERATOR_NAME;
    }
}
