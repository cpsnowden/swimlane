package org.cps.swimlane.venues.activelambeth.external;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.cps.swimlane.venues.better.external.ResponseData;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.time.LocalDate;

@RegisterRestClient(baseUri = "https://flow.onl/api")
@ClientHeaderParam(name="origin", value="https://lambethcouncil.bookings.flow.onl")
public interface ActiveLambethApi {

    //    Fix this response type
    @GET
    @Path("activities/venues")
    @Produces(MediaType.APPLICATION_JSON)
    ResponseData<Venue> venues();

    @GET
    @Path("activities/venue/{venue}/activity/adult-lane-swimming/times")
    @Produces(MediaType.APPLICATION_JSON)
    ResponseData<Activity> adultLaneSwimmingTimes(@PathParam("venue") String venue, @QueryParam("date") LocalDate date);
}
