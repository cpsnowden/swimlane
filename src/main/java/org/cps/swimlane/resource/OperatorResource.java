package org.cps.swimlane.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.cps.swimlane.model.LaneAvailability;
import org.cps.swimlane.model.Operator;
import org.cps.swimlane.model.Venue;
import org.cps.swimlane.operator.core.PoolVenueOperatorRegistry;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Path("operators")
public class OperatorResource {

    @Inject
    PoolVenueOperatorRegistry poolVenueOperatorRegistry;

    @GET
    public List<Operator> operators() {
        return poolVenueOperatorRegistry.all()
                .map(operator -> new Operator(operator.getOperatorId()))
                .collect(Collectors.toList());
    }

    @GET
    @Path("{operatorId}/venues")
    public List<Venue> operatorVenues(@PathParam("operatorId") String operatorId) {
        return poolVenueOperatorRegistry.findWithName(operatorId)
                .orElseThrow(() -> new NotFoundException("Unknown operator " + operatorId))
                .getVenues();
    }

    @GET
    @Path("{operatorId}/venues/{venueId}/times/{date}")
    public List<LaneAvailability> operatorVenueTimes(@PathParam("operatorId") String operatorId,
                                                     @PathParam("venueId") String venueId,
                                                     @PathParam("date") LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new BadRequestException("Date " + date + "is in the past");
        }
        return poolVenueOperatorRegistry
                .findWithNameOrThrow(operatorId)
                .getAvailability(venueId, date)
                .stream()
                .sorted(Comparator.comparing(LaneAvailability::getStartAtUTC))
                .collect(Collectors.toList());
    }
}
