package org.cps.swimlane.operator.plugin.everyoneactive.external;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://caching.everyoneactive.com/aws/api")
public interface EveryoneActiveApi {
    @GET
    @Path("session")
    @Produces(MediaType.APPLICATION_JSON)
    SessionResponse sessions(@HeaderParam("authenticationkey") String authenticationKey,
                     @QueryParam("scopeIds") String scopeIds,
                     @QueryParam("fromUTC") long fromUTC,
                     @QueryParam("toUTC") long toUTC);
}
