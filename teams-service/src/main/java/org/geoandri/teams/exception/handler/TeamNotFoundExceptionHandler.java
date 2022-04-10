package org.geoandri.teams.exception.handler;

import org.geoandri.teams.exception.TeamNotFoundException;
import org.geoandri.teams.exception.error.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TeamNotFoundExceptionHandler implements ExceptionMapper<TeamNotFoundException> {
    @Override
    public Response toResponse(TeamNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(e.getMessage())).build();
    }
}
