package org.cps.swimlane.venues.better;

import jakarta.enterprise.context.ApplicationScoped;
import org.cps.swimlane.model.LaneAvailability;
import org.cps.swimlane.model.Venue;
import org.cps.swimlane.venues.operator.PoolVenueOperator;
import org.cps.swimlane.venues.better.external.BetterHealthApi;
import org.cps.swimlane.venues.better.external.FormatTime;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BetterHealth implements PoolVenueOperator {

    public static final String OPERATOR_NAME = "better-health";

    @RestClient
    BetterHealthApi betterHealthApi;

    @Override
    public List<LaneAvailability> getAvailability(String venueSlug, LocalDate date) {
        return betterHealthApi.adultLaneSwimmingTimes(venueSlug, date)
                .getData()
                .stream()
                .map(activity -> new LaneAvailability(
                        activity.getDate(),
                        toDateTime(activity.getDate(), activity.getStartsAt()),
                        toDateTime(activity.getDate(), activity.getEndsAt()),
                        OPERATOR_NAME,
                        activity.getVenueSlug(),
                        activity.getLocation(),
                        activity.getSpaces())
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Venue> getVenues() {
        return betterHealthApi.venues()
                .getData()
                .stream()
                .map(venue -> new Venue(venue.getName(), venue.getSlug(), OPERATOR_NAME))
                .collect(Collectors.toList());
    }

    @Override
    public String getOperatorName() {
        return OPERATOR_NAME;
    }

    private LocalDateTime toDateTime(LocalDate date, FormatTime time) {
        return date.atTime(time.getFormat24Hour());
    }
}
