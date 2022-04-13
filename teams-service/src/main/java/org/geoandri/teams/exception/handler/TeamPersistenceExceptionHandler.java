package org.geoandri.teams.exception.handler;

import org.geoandri.teams.exception.TeamPersistenceException;
import org.geoandri.teams.exception.error.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TeamPersistenceExceptionHandler implements ExceptionMapper<TeamPersistenceException> {
    @Override
    public Response toResponse(TeamPersistenceException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(e.getMessage())).build();
    }
}
