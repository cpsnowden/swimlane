package org.cps.swimlane.operator.plugin.lbrut.external;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://gladapi.richmond.gov.uk/AWS/api")
public interface LBRUTApi {
    @GET
    @Path("session")
    @Produces(MediaType.APPLICATION_JSON)
    SessionResponse sessions(@HeaderParam("authenticationkey") String authenticationKey,
                             @QueryParam("scopeIds") String scopeIds);
}
