package org.geoandri.developers.consumer;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import org.geoandri.developers.dto.TeamDto;
import org.geoandri.developers.event.EventType;
import org.geoandri.developers.event.TeamEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusTest
@QuarkusTestResource(KafkaCompanionResource.class)
public class TeamConsumerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamConsumerTest.class);

    @InjectKafkaCompanion
    KafkaCompanion companion;

    @Test
    public void testTeamConsumer() {
        System.out.println("++++++++++++++++++++++++++++++starting test");
        TeamDto teamDto = new TeamDto();
        teamDto.setId(50);
        teamDto.setName("Another new Team");
        teamDto.setDescription("Another description");

        TeamEvent teamEvent = new TeamEvent(EventType.TEAM_CREATED, teamDto);

        Assertions.assertEquals(0,1);
        System.out.println("++++++++++++++++++ test end");
    }
}
