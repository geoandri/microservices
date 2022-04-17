package org.geoandri.developers.resource;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.geoandri.developers.dto.DeveloperDto;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(DeveloperResource.class);

    @Inject
    DeveloperService developerService;

    @Inject
    DeveloperMapper developerMapper;

    @GET
    @APIResponse(
            responseCode = "200",
            description = "Get All Developers",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.ARRAY, implementation = DeveloperDto.class)
            )
    )
    public Response getDevelopers(@DefaultValue("1") @QueryParam("pageNum") int pageNum,
                                  @DefaultValue("20") @QueryParam("pageSize") int pageSize,
                                  @QueryParam("teamId") long teamId) {
        LOGGER.debug("Receive request to get developers with parameters pageNum: {}, pageSize: {}, teamId: {}",
                pageNum, pageSize, teamId);
        List<DeveloperDto> developerDtoList = developerMapper.map(developerService.getAll(pageNum, pageSize, teamId));

        return Response
                .status(Response.Status.OK)
                .entity(developerDtoList)
                .build();
    }

    @GET
    @Path("/{id}")
    @APIResponse(
            responseCode = "200",
            description = "Get a developer",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = DeveloperDto.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Developer not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getDeveloper(@PathParam("id") long id) {
        LOGGER.debug("Receive request to get developer with id: {}", id);
        DeveloperDto developerDto = developerMapper.toDeveloperDto(developerService.get(id));

        return Response
                .status(Response.Status.OK)
                .entity(developerDto)
                .build();
    }

    @POST
    @APIResponse(
            responseCode = "201",
            description = "Developer created",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = DeveloperDto.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid DeveloperDto",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Team provided not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response saveDeveloper(@Valid DeveloperDto developerDto) {
        LOGGER.debug("Receive request to save  developer {}", developerDto);
        DeveloperDto persistedDeveloperDto = developerMapper.
                toDeveloperDto(developerService.save(developerMapper.toDeveloper(developerDto)));

        return Response
                .status(Response.Status.CREATED)
                .entity(persistedDeveloperDto)
                .build();
    }

    @PUT
    @Path("/{id}")
    @APIResponse(
            responseCode = "200",
            description = "Developer updated",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = DeveloperDto.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid DeveloperDto",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "404",
            description = "Developer not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Team not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response updateDeveloper(@PathParam("id") long id, DeveloperDto developerDto) {
        LOGGER.debug("Receive request to update developer with id: {} and updated data {}", id, developerDto);
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
    @APIResponse(
            responseCode = "200",
            description = "Developer deleted",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = DeveloperDto.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Developer not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response deleteDeveloper(@PathParam("id") long id) {
        LOGGER.debug("Receive request to delete developer with id: {}", id);
        developerService.delete(id);

        return Response
                .status(Response.Status.OK).build();
    }
}
