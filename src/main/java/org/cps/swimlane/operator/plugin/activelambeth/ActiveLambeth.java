package org.cps.swimlane.operator.plugin.activelambeth;

import jakarta.enterprise.context.ApplicationScoped;
import org.cps.swimlane.model.LaneAvailability;
import org.cps.swimlane.model.Venue;
import org.cps.swimlane.operator.core.PoolVenueOperator;
import org.cps.swimlane.operator.plugin.better.external.ExternalApi;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ActiveLambeth implements PoolVenueOperator {

    public static final String OPERATOR_NAME = "active-lambeth";

    @RegisterRestClient(baseUri = "https://flow.onl/api")
    @ClientHeaderParam(name="origin", value="https://lambethcouncil.bookings.flow.onl")
    interface ActiveLambethApi extends ExternalApi {}

    @RestClient
    ActiveLambethApi activeLambethApi;

    @Override
    public String getOperatorId() {
        return OPERATOR_NAME;
    }

    @Override
    public List<Venue> getVenues() {
        return activeLambethApi.venues()
                .getData()
                .stream()
                .map(venue -> new Venue(venue.getName(), venue.getSlug(), OPERATOR_NAME))
                .collect(Collectors.toList());
    }

    @Override
    public List<LaneAvailability> getAvailability(String venueSlug, LocalDate date) {
        return activeLambethApi.activityTimes(venueSlug, "adult-lane-swimming", date)
                .getData()
                .stream()
                .map(activity -> new LaneAvailability(
                        activity.getDate(),
                        activity.getStartsAt(),
                        activity.getEndsAt(),
                        OPERATOR_NAME,
                        activity.getVenueSlug(),
                        activity.getLocation(),
                        activity.getSpaces())
                )
                .collect(Collectors.toList());
    }
}
