package org.geoandri.developers.exception.handler;

import org.geoandri.developers.exception.EntityNotFoundException;
import org.geoandri.developers.exception.error.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionHandler  implements ExceptionMapper<EntityNotFoundException> {
    @Override
    public Response toResponse(EntityNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(e.getMessage())).build();
    }
}
