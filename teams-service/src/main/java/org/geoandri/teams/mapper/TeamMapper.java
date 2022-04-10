package org.geoandri.teams.mapper;

import org.geoandri.teams.dto.TeamDto;
import org.geoandri.teams.entity.Team;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface TeamMapper {
    List<TeamDto> map(List<Team> teams);

    TeamDto toTeamDto(Team team);

    Team toTeam(TeamDto teamDto);
}
