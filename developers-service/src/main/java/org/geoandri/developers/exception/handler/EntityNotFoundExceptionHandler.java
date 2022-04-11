package org.geoandri.developers.exception.handler;

import org.geoandri.developers.exception.EntityNotFoundException;
import org.geoandri.developers.exception.error.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionHandler  implements ExceptionMapper<EntityNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityNotFoundExceptionHandler.class);

    @Override
    public Response toResponse(EntityNotFoundException e) {
        LOGGER.warn(e.getMessage());

        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(e.getMessage())).build();
    }
}
