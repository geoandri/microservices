package org.geoandri.developers.consumer;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.geoandri.developers.dto.TeamDto;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/teams")
@RegisterRestClient
public interface TeamServiceClient {

    @POST
    TeamDto saveTeam(TeamDto teamDto);
}
