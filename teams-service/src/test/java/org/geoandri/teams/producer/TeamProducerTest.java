package org.geoandri.teams.producer;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.ConsumerTask;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.geoandri.teams.dto.TeamDto;
import org.geoandri.teams.event.EventType;
import org.geoandri.teams.event.TeamEvent;
import org.gradle.internal.impldep.javax.inject.Inject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;

@QuarkusTestResource(KafkaCompanionResource.class)
@QuarkusTest
public class TeamProducerTest {

    @InjectKafkaCompanion
    KafkaCompanion companion;

    @Inject
    TeamProducer teamProducer;

    @Test
    public void testTeamProducer() {

        System.out.println("++++++++++++++++++++++++++++++starting test");
        TeamDto teamDto = new TeamDto();
        teamDto.setId(50);
        teamDto.setName("Another new Team");
        teamDto.setDescription("Another description");

        TeamEvent teamEvent = new TeamEvent(EventType.TEAM_CREATED, teamDto);

        teamProducer.publishEvent(teamEvent);
        ConsumerTask<Integer, TeamEvent> consumerTask = companion.consume(Integer.class, TeamEvent.class)
                .fromTopics("team-events");
        ConsumerRecord<Integer, TeamEvent> receivedEvent = consumerTask.awaitCompletion().getFirstRecord();

        System.out.println("++++++++++++++++++" + receivedEvent.value().getEventType());

        Assertions.assertEquals(EventType.TEAM_UPDATED, receivedEvent.value().getEventType());
    }

}
