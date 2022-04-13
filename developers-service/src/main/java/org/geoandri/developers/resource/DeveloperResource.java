package org.geoandri.developers.resource;

import io.quarkus.test.junit.mockito.InjectSpy;
import org.geoandri.developers.dto.DeveloperDto;
import org.geoandri.developers.exception.EntityNotFoundException;
import org.geoandri.developers.mapper.DeveloperMapper;
import org.geoandri.developers.service.DeveloperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/developers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeveloperResource {
    private static final Logger logger = LoggerFactory.getLogger(DeveloperResource.class);

    @Inject
    DeveloperService developerService;

    @Inject
    DeveloperMapper developerMapper;

    @GET
    public Response getDevelopers(@DefaultValue("1") @QueryParam("pageNum") int pageNum,
                                  @DefaultValue("20") @QueryParam("pageSize") int pageSize,
                                  @QueryParam("teamId") long teamId) {
        List<DeveloperDto> developerDtoList = developerMapper.map(developerService.getAll(pageNum, pageSize, teamId));

        return Response
                .status(Response.Status.OK)
                .entity(developerDtoList)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getDeveloper(@PathParam("id") long id) throws EntityNotFoundException {
        DeveloperDto developerDto = developerMapper.toDeveloperDto(developerService.get(id));

        return Response
                .status(Response.Status.OK)
                .entity(developerDto)
                .build();
    }

    @POST
    public Response saveDeveloper(@Valid DeveloperDto developerDto) throws EntityNotFoundException {
        DeveloperDto persistedDeveloperDto = developerMapper.
                toDeveloperDto(developerService.save(developerMapper.toDeveloper(developerDto)));

        return Response
                .status(Response.Status.CREATED)
                .entity(persistedDeveloperDto)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateDeveloper(@PathParam("id") long id, DeveloperDto developerDto) throws EntityNotFoundException {
        developerDto.setId(id);
        DeveloperDto updatedDeveloperDto = developerMapper.
                toDeveloperDto(developerService.update(developerMapper.toDeveloper(developerDto)));

        return Response
                .status(Response.Status.OK)
                .entity(updatedDeveloperDto)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDeveloper(@PathParam("id") long id) throws EntityNotFoundException {
        developerService.delete(id);

        return Response
                .status(Response.Status.OK).build();
    }
}
