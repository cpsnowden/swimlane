package org.cps.swimlane.venues.better.external;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.time.LocalDate;

@RegisterRestClient(baseUri = "https://better-admin.org.uk/api")
@ClientHeaderParam(name="origin", value="https://bookings.better.org.uk")
public interface BetterHealthApi {
    @GET
    @Path("activities/venues")
    @Produces(MediaType.APPLICATION_JSON)
    ResponseData<Venue> venues();
    @GET
    @Path("activities/venue/{venue}/activity/swim-for-fitness/times")
    @Produces(MediaType.APPLICATION_JSON)
    ResponseData<Activity> adultLaneSwimmingTimes(@PathParam("venue") String venue, @QueryParam("date") LocalDate date);
}
