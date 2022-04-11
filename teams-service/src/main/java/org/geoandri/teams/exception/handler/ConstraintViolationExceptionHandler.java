package org.geoandri.teams.exception.handler;

import org.geoandri.teams.exception.error.ErrorMessage;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<String> errorMessages = e.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .collect(Collectors.toList());

        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }
}
