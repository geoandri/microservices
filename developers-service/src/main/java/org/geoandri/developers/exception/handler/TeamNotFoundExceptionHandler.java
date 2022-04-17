package org.geoandri.developers.exception.handler;

import org.geoandri.developers.exception.TeamNotFoundException;
import org.geoandri.developers.exception.error.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TeamNotFoundExceptionHandler implements ExceptionMapper<TeamNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamNotFoundExceptionHandler.class);

    @Override
    public Response toResponse(TeamNotFoundException e) {
        LOGGER.warn(e.getMessage());

        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(e.getMessage())).build();
    }
}
