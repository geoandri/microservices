package org.geoandri.developers.mapper;

import org.geoandri.developers.dto.DeveloperDto;
import org.geoandri.developers.entity.Developer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi", uses = { TeamMapper.class })
public interface DeveloperMapper {

    @Mapping(target = "team", source = "team.name")
    List<DeveloperDto> map(List<Developer> teams);

    @Mapping(target = "team", source = "team.name")
    DeveloperDto toDeveloperDto(Developer team);

    @Mapping(target = "team.name", source = "team")
    Developer toDeveloper(DeveloperDto teamDto);
}
