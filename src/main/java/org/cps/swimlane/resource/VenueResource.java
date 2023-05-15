package org.cps.swimlane.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.cps.swimlane.model.Venue;
import org.cps.swimlane.operator.core.PoolVenueOperator;
import org.cps.swimlane.operator.core.PoolVenueOperatorRegistry;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Path("venues")
public class VenueResource {

    @Inject
    PoolVenueOperatorRegistry poolVenueOperators;

    @GET
    public List<Venue> venues() {
        return poolVenueOperators.all()
                .sorted(Comparator.comparing(PoolVenueOperator::getOperatorId))
                .map(PoolVenueOperator::getVenues)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
