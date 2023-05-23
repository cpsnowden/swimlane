package org.cps.swimlane.operator.plugin.lbrut;

import jakarta.enterprise.context.ApplicationScoped;
import org.cps.swimlane.model.LaneAvailability;
import org.cps.swimlane.model.Venue;
import org.cps.swimlane.operator.core.PoolVenueOperator;
import org.cps.swimlane.operator.plugin.everyoneactive.VenueDetails;
import org.cps.swimlane.operator.plugin.lbrut.external.LBRUTApi;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class LBRUT implements PoolVenueOperator {

    public static final String OPERATOR_NAME = "lbrut";

    //Expand once know how to correlate venues with swimming activity id
    private final Map<String, VenueDetails> venuesBySlug = Stream.of(
            new VenueDetails("Pools On The Park", "pools-on-the-park")
                                .withScope("PDD12").withScope("PDD13").withScope("PDD14")
                                .withScope("PDD09").withScope("PDD10").withScope("PDD11")
    ).collect(Collectors.toMap(VenueDetails::getSlug, Function.identity()));

    @ConfigProperty(name = "lbrut.api-authentication-key")
    String apiAuthenticationKey;

    @RestClient
    LBRUTApi lbrutApi;

    @Override
    public List<LaneAvailability> getAvailability(String venueSlug, LocalDate date) {

        VenueDetails venueDetails = venuesBySlug.get(venueSlug);

        Instant start = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant end = date.atTime(23, 59, 59).toInstant(ZoneOffset.UTC);
        return lbrutApi.sessions(apiAuthenticationKey, String.join(",", venueDetails.getSessionScope()))
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
