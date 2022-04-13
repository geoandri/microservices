package org.geoandri.developers.consumer;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.geoandri.developers.dao.TeamDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@QuarkusTest
public class TeamConsumerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamConsumerTest.class);

    @Inject
    @RestClient
    TeamServiceClient teamServiceClient;

    @Inject
    TeamDao teamDao;

//    @Test
//    public void testTeamConsumer() {
//        TeamDto teamDto = new TeamDto();
//        teamDto.setName("Test new Team");
//
//        TeamDto persistedTeamDto = teamServiceClient.saveTeam(teamDto);
//
//        try {
//            Assertions.assertEquals(teamDto.getId(), teamDao.getTeam(persistedTeamDto.getId()).getId());
//        } catch (EntityNotFoundException e) {
//            LOGGER.error(e.getMessage());
//        }
//    }
}
