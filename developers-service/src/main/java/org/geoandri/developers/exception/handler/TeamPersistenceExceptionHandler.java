package org.geoandri.developers.exception.handler;

import org.geoandri.developers.exception.EntityPersistenceException;
import org.geoandri.developers.exception.error.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TeamPersistenceExceptionHandler implements ExceptionMapper<EntityPersistenceException> {
    @Override
    public Response toResponse(EntityPersistenceException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(e.getMessage())).build();
    }
}
