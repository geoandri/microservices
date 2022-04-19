package org.geoandri.teams.resource;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.geoandri.teams.dto.TeamDto;
import org.geoandri.teams.event.EventType;
import org.geoandri.teams.event.TeamEvent;
import org.geoandri.teams.mapper.TeamMapper;
import org.geoandri.teams.producer.TeamProducer;
import org.geoandri.teams.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/teams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeamResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamResource.class);

    @Inject
    TeamService teamService;

    @Inject
    TeamMapper teamMapper;

    @Inject
    TeamProducer teamProducer;

    @GET
    @APIResponse(
            responseCode = "200",
            description = "Get All Teams",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.ARRAY, implementation = TeamDto.class)
            )
    )
    public Response getTeams(@DefaultValue("1") @QueryParam("pageNum") int pageNum,
                             @DefaultValue("20") @QueryParam("pageSize") int pageSize) {
        LOGGER.debug("Received request to get teams with parameters pageNum: {}, pageSize: {}", pageNum, pageSize);
        List<TeamDto> teamDtoList = teamMapper.map(teamService.getAll(pageNum, pageSize));

        return Response
                .status(Response.Status.OK)
                .entity(teamDtoList)
                .build();
    }

    @GET
    @Path("/{id}")
    @APIResponse(
            responseCode = "200",
            description = "Get a team",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = TeamDto.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Team not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getTeam(@PathParam("id") long id) {
        LOGGER.debug("Received request to get team with id: {}", id);
        TeamDto teamDto = teamMapper.toTeamDto(teamService.get(id));

        return Response
                .status(Response.Status.OK)
                .entity(teamDto)
                .build();
    }

    @POST
    @APIResponse(
            responseCode = "201",
            description = "Team created",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = TeamDto.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid TeamDto",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response saveTeam(@Valid TeamDto teamDto) {
        LOGGER.debug("Received request to save team {}", teamDto);
        TeamDto persistedTeamDto = teamMapper.toTeamDto(teamService.save(teamMapper.toTeam(teamDto)));
        teamProducer.publishEvent(new TeamEvent(EventType.TEAM_CREATED, persistedTeamDto));

        return Response
                .status(Response.Status.CREATED)
                .entity(persistedTeamDto)
                .build();
    }

    @PUT
    @Path("/{id}")
    @APIResponse(
            responseCode = "200",
            description = "Team updated",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = TeamDto.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid Team",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "404",
            description = "Team not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response updateTeam(@PathParam("id") long id, TeamDto teamDto) {
        LOGGER.debug("Received request to update developer with id: {} and updated data: {}", id, teamDto);
        TeamDto updatedTeamDto = teamMapper.toTeamDto(teamService.update(id, teamMapper.toTeam(teamDto)));
        teamProducer.publishEvent(new TeamEvent(EventType.TEAM_UPDATED, updatedTeamDto));

        return Response
                .status(Response.Status.OK)
                .entity(updatedTeamDto)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @APIResponse(
            responseCode = "200",
            description = "Team deleted",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "404",
            description = "Team not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response deleteTeam(@PathParam("id") long id) {
        LOGGER.debug("Received request to delete developer with id: {}", id);
        teamService.delete(id);
        teamProducer.publishEvent(new TeamEvent(EventType.TEAM_DELETED, new TeamDto(id, "FOR DELETION")));

        return Response
                .status(Response.Status.OK).build();
    }
}
