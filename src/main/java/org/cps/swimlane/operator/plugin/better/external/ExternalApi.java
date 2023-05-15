package org.cps.swimlane.operator.plugin.better.external;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDate;

public interface ExternalApi {

    @GET
    @Path("activities/venues")
    @Produces(MediaType.APPLICATION_JSON)
    VenueResponse venues();

    @GET
    @Path("activities/venue/{venue}/activity/{activity}/times")
    @Produces(MediaType.APPLICATION_JSON)
    ActivityResponse activityTimes(@PathParam("venue") String venue,
                                   @PathParam("activity") String activity,
                                   @QueryParam("date") LocalDate date);
}
