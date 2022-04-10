package org.geoandri.developers.mapper;

import org.geoandri.developers.dto.TeamDto;
import org.geoandri.developers.entity.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface TeamMapper {

    TeamDto toTeamDto(Team team);

    Team toTeam(TeamDto teamDto);
}
