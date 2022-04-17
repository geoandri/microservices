package org.geoandri.developers.exception.handler;

import org.geoandri.developers.exception.DeveloperNotFoundException;
import org.geoandri.developers.exception.error.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DeveloperNotFoundExceptionHandler implements ExceptionMapper<DeveloperNotFoundException> {
    @Override
    public Response toResponse(DeveloperNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(e.getMessage())).build();
    }
}
