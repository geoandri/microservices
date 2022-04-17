package org.geoandri.developers.exception.handler;

import org.geoandri.developers.exception.TeamNotFoundException;
import org.geoandri.developers.exception.error.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TeamNotFoundExceptionHandler implements ExceptionMapper<TeamNotFoundException> {

    @Override
    public Response toResponse(TeamNotFoundException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(e.getMessage())).build();
    }
}
