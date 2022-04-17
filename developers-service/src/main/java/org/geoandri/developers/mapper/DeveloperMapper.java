package org.geoandri.developers.mapper;

import org.geoandri.developers.dto.DeveloperDto;
import org.geoandri.developers.entity.Developer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi", uses = { TeamMapper.class })
public interface DeveloperMapper {

    @Mapping(target = "teamId", source = "team.id")
    List<DeveloperDto> map(List<Developer> teams);

    @Mapping(target = "teamId", source = "team.id")
    DeveloperDto toDeveloperDto(Developer team);

    @Mapping(target = "team.id", source = "teamId")
    Developer toDeveloper(DeveloperDto teamDto);
}
