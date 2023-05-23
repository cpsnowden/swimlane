package org.cps.swimlane.operator.plugin.everyoneactive;

import jakarta.enterprise.context.ApplicationScoped;
import org.cps.swimlane.model.LaneAvailability;
import org.cps.swimlane.model.Venue;
import org.cps.swimlane.operator.core.PoolVenueOperator;
import org.cps.swimlane.operator.plugin.everyoneactive.external.EveryoneActiveApi;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class EveryoneActive implements PoolVenueOperator {

    public static final String OPERATOR_NAME = "everyone-active";

    //Expand once know how to correlate venues with swimming activity id
    private final Map<String, VenueDetails> venuesBySlug = Stream.of(
            new VenueDetails("Queen Mother Sports Centre", "queen-mother-sports-center").withScope("155POOLPR1")
    ).collect(Collectors.toMap(VenueDetails::getSlug, Function.identity()));

    @ConfigProperty(name = "everyone-active.api-authentication-key")
    String apiAuthenticationKey;

    @RestClient
    EveryoneActiveApi everyoneActiveApi;

    @Override
    public List<LaneAvailability> getAvailability(String venueSlug, LocalDate date) {

        VenueDetails venueDetails = venuesBySlug.get(venueSlug);

        long fromUTC = date.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
        long toUTC = date.atTime(23, 59, 59).toEpochSecond(ZoneOffset.UTC);
        return everyoneActiveApi.sessions(apiAuthenticationKey, String.join(",", venueDetails.getSessionScope()), fromUTC, toUTC)
                .getSessions()
                .stream()
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
        return venuesBySlug.values()
                .stream()
                .map(venueDetails -> new Venue(venueDetails.getName(), venueDetails.getSlug(), OPERATOR_NAME))
                .collect(Collectors.toList());
    }

    @Override
    public String getOperatorId() {
        return OPERATOR_NAME;
    }
}
