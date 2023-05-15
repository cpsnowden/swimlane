package org.cps.swimlane.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.cps.swimlane.model.LaneAvailability;
import org.cps.swimlane.model.Venue;
import org.cps.swimlane.venues.activelambeth.ActiveLambeth;
import org.cps.swimlane.venues.better.BetterHealth;
import org.cps.swimlane.venues.everyoneactive.EveryoneActive;
import org.cps.swimlane.venues.operator.PoolVenueOperator;
import org.cps.swimlane.venues.operator.PoolVenueOperatorRegistry;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//Cleanup these api routes
@Path("")
public class LaneAvailabilityResource {

    //Remove as can be stored in UI local storage
    private static final List<VenueId> FAVORITES = Arrays.asList(
            new VenueId(BetterHealth.OPERATOR_NAME, "vauxhall-leisure-centre"),
            new VenueId(ActiveLambeth.OPERATOR_NAME, "clapham-leisure-centre"),
            new VenueId(EveryoneActive.OPERATOR_NAME, "155POOLPR1")
    );

    @Inject
    PoolVenueOperatorRegistry poolVenueOperators;

    @GET
    @Path("venues")
    public List<Venue> venues() {
        return poolVenueOperators.all()
                .sorted(Comparator.comparing(PoolVenueOperator::getOperatorName))
                .map(PoolVenueOperator::getVenues)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @GET
    @Path("venues/favorites/times/{date}")
    public List<LaneAvailability> favoriteVenueTimes(@PathParam("date") LocalDate date) {
        return FAVORITES
                .parallelStream()
                .map(favorite -> venueTimes(favorite.operator, favorite.venueSlug, date))
                .flatMap(List::stream)
                .sorted(Comparator.comparing(LaneAvailability::getStartAtUTC))
                .collect(Collectors.toList());
    }

    @GET
    @Path("venues/{operator}/{venueSlug}/times/{date}")
    public List<LaneAvailability> venueTimes(@PathParam("operator") String operator,
                                             @PathParam("venueSlug") String venueSlug,
                                             @PathParam("date") LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new BadRequestException("Date " + date + "is in the past");
        }
        return poolVenueOperators
                .findWithName(operator)
                .orElseThrow(() -> new BadRequestException("Unknown operator " + operator))
                .getAvailability(venueSlug, date);
    }

    private static class VenueId {
        private final String operator;
        private final String venueSlug;

        public VenueId(String operator, String venueSlug) {
            this.operator = operator;
            this.venueSlug = venueSlug;
        }
    }
}
