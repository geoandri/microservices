package org.geoandri.developers.mapper;

import io.quarkus.test.junit.QuarkusTest;
import org.geoandri.developers.dto.DeveloperDto;
import org.geoandri.developers.entity.Developer;
import org.geoandri.developers.entity.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class DeveloperMapperTest {

    private static DeveloperDto developerDto;
    private static Developer developer;
    private static Team team;

    @Inject
    DeveloperMapper developerMapper;

    @BeforeAll
    private static void setup() {
        team = new Team();
        team.setId(1L);

        developer = new Developer();
        developer.setId(1L);
        developer.setName("Developer1");
        developer.setTeam(team);

        developerDto = new DeveloperDto();
        developerDto.setId(1L);
        developerDto.setName("Developer1");
        developerDto.setTeamId(team.getId());
    }

    @Test
    public void testDeveloperToDeveloperDto() {
        DeveloperDto mappedDeveloperDto = developerMapper.toDeveloperDto(developer);

        assertEquals(developerDto.getId(), mappedDeveloperDto.getId());
        assertEquals(developerDto.getName(), mappedDeveloperDto.getName());
        assertEquals(developerDto.getTeamId(), mappedDeveloperDto.getTeamId());
    }
}
