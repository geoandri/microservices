package org.geoandri.developers.consumer;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.geoandri.developers.dto.TeamDto;
import org.geoandri.developers.event.EventType;
import org.geoandri.developers.event.TeamEvent;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

//@QuarkusTest
//@QuarkusTestResource(KafkaCompanionResource.class)
public class TeamConsumerTest {

    @InjectKafkaCompanion
    KafkaCompanion companion;

    @InjectSpy
    TeamConsumer teamConsumer;

    @Test
    @Ignore
    public void testTeamConsumer() {
        TeamDto teamDto = new TeamDto();
        teamDto.setId(50);
        teamDto.setName("Another new Team");
        teamDto.setDescription("Another description");

        TeamEvent teamEvent = new TeamEvent(EventType.TEAM_CREATED, teamDto);

        companion.registerSerde(TeamEvent.class, new TeamEventSerializer(), new TeamEventDeserializer());

        companion.produce(Integer.class, TeamEvent.class)
                .fromRecords(new ProducerRecord<>("team-events", 1, teamEvent)).awaitCompletion();

        Mockito.verify(teamConsumer, Mockito.timeout(5000).times(1))
                .consumeEvents(teamEvent);
    }
}
