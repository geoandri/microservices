package org.geoandri.teams.resource;

import org.geoandri.teams.dto.TeamDto;
import org.geoandri.teams.exception.TeamNotFoundException;
import org.geoandri.teams.mapper.TeamMapper;
import org.geoandri.teams.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class TeamResource {

    private static final Logger logger = LoggerFactory.getLogger(TeamResource.class);

    @Inject
    TeamService teamService;

    @Inject
    TeamMapper teamMapper;

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
    public Response getTeam(@PathParam("id") long id) throws TeamNotFoundException {
        TeamDto teamDto = teamMapper.toTeamDto(teamService.get(id));

        return Response
                .status(Response.Status.OK)
                .entity(teamDto)
                .build();
    }

    @POST
    public Response saveTeam(@Valid TeamDto teamDto) {
        TeamDto persistedTeamDto = teamMapper.toTeamDto(teamService.save(teamMapper.toTeam(teamDto)));

        return Response
                .status(Response.Status.CREATED)
                .entity(persistedTeamDto)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTeam(@PathParam("id") long id, TeamDto teamDto) throws TeamNotFoundException {
        TeamDto updatedTeamDto = teamMapper.toTeamDto(teamService.update(id, teamMapper.toTeam(teamDto)));

        return Response
                .status(Response.Status.OK)
                .entity(updatedTeamDto)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTeam(@PathParam("id") long id) throws TeamNotFoundException {
        teamService.delete(id);

        return Response
                .status(Response.Status.OK).build();

    }
}
