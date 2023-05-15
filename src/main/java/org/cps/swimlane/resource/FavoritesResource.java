package org.cps.swimlane.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.cps.swimlane.model.LaneAvailability;
import org.cps.swimlane.operator.plugin.activelambeth.ActiveLambeth;
import org.cps.swimlane.operator.plugin.better.BetterHealth;
import org.cps.swimlane.operator.plugin.everyoneactive.EveryoneActive;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Deprecated
@Path("favorites-venues")
public class FavoritesResource {

    private static final List<VenueId> FAVORITES = Arrays.asList(
            new VenueId(BetterHealth.OPERATOR_NAME, "vauxhall-leisure-centre"),
            new VenueId(ActiveLambeth.OPERATOR_NAME, "clapham-leisure-centre"),
            new VenueId(EveryoneActive.OPERATOR_NAME, "155POOLPR1")
    );

    @Inject
    OperatorResource operatorResource;

    @GET
    @Path("times/{date}")
    public List<LaneAvailability> favoriteVenueTimes(@PathParam("date") LocalDate date) {
        return FAVORITES
                .parallelStream()
                .map(favorite -> operatorResource.operatorVenueTimes(favorite.operator, favorite.venueSlug, date))
                .flatMap(List::stream)
                .sorted(Comparator.comparing(LaneAvailability::getStartAtUTC))
                .collect(Collectors.toList());
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
