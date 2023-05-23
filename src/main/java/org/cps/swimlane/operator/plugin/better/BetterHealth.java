package org.cps.swimlane.operator.plugin.better;

import jakarta.enterprise.context.ApplicationScoped;
import org.cps.swimlane.model.LaneAvailability;
import org.cps.swimlane.model.Venue;
import org.cps.swimlane.operator.core.PoolVenueOperator;
import org.cps.swimlane.operator.plugin.better.external.ExternalApi;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BetterHealth implements PoolVenueOperator {

    public static final String OPERATOR_NAME = "better-health";

    @RegisterRestClient(baseUri = "https://better-admin.org.uk/api")
    @ClientHeaderParam(name="origin", value="https://bookings.better.org.uk")
    interface BetterHealthApi extends ExternalApi {}

    @RestClient
    BetterHealthApi betterHealthApi;

    @Override
    public String getOperatorId() {
        return OPERATOR_NAME;
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
    public List<LaneAvailability> getAvailability(String venueSlug, LocalDate date) {
        return betterHealthApi.activityTimes(venueSlug, "swim-for-fitness", date)
                .getData()
                .stream()
                .map(activity -> new LaneAvailability(
                        activity.getDate(),
                        activity.getStartsAt().atZone(ZoneId.of("Europe/London")).toInstant(),
                        activity.getEndsAt().atZone(ZoneId.of("Europe/London")).toInstant(),
                        OPERATOR_NAME,
                        activity.getVenueSlug(),
                        activity.getLocation(),
                        activity.getSpaces())
                )
                .collect(Collectors.toList());
    }
}
