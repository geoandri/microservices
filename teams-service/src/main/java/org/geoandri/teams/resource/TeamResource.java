package org.geoandri.teams.resource;

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
    public Response getTeams(@DefaultValue("1") @QueryParam("pageNum") int pageNum,
                             @DefaultValue("20") @QueryParam("pageSize") int pageSize) {
        List<TeamDto> teamDtoList = teamMapper.map(teamService.getAll(pageNum, pageSize));

        return Response
                .status(Response.Status.OK)
                .entity(teamDtoList)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getTeam(@PathParam("id") long id) {
        TeamDto teamDto = teamMapper.toTeamDto(teamService.get(id));

        return Response
                .status(Response.Status.OK)
                .entity(teamDto)
                .build();
    }

    @POST
    public Response saveTeam(@Valid TeamDto teamDto) {
        LOGGER.debug("Received request to save teamDto: {}", teamDto);
        TeamDto persistedTeamDto = teamMapper.toTeamDto(teamService.save(teamMapper.toTeam(teamDto)));
        teamProducer.publishEvent(new TeamEvent(EventType.TEAM_CREATED, persistedTeamDto));

        return Response
                .status(Response.Status.CREATED)
                .entity(persistedTeamDto)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTeam(@PathParam("id") long id, TeamDto teamDto) {
        TeamDto updatedTeamDto = teamMapper.toTeamDto(teamService.update(id, teamMapper.toTeam(teamDto)));
        teamProducer.publishEvent(new TeamEvent(EventType.TEAM_UPDATED, updatedTeamDto));

        return Response
                .status(Response.Status.OK)
                .entity(updatedTeamDto)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTeam(@PathParam("id") long id) {
        teamService.delete(id);
        teamProducer.publishEvent(new TeamEvent(EventType.TEAM_DELETED, new TeamDto(id, "FOR DELETION")));

        return Response
                .status(Response.Status.OK).build();
    }
}
