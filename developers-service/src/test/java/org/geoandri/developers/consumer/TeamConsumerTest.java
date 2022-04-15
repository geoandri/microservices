package org.geoandri.developers.consumer;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import io.quarkus.kafka.client.serialization.ObjectMapperSerializer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import io.smallrye.reactive.messaging.kafka.companion.ProducerBuilder;
import io.smallrye.reactive.messaging.kafka.companion.ProducerTask;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.geoandri.developers.dto.TeamDto;
import org.geoandri.developers.event.EventType;
import org.geoandri.developers.event.TeamEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@QuarkusTest
@QuarkusTestResource(KafkaCompanionResource.class)
public class TeamConsumerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamConsumerTest.class);

    @InjectKafkaCompanion
    KafkaCompanion companion;

    @InjectSpy
    TeamConsumer teamConsumer;

    @Test
    public void testTeamConsumer() {
        System.out.println("++++++++++++++++++++++++++++++starting test");
        TeamDto teamDto = new TeamDto();
        teamDto.setId(50);
        teamDto.setName("Another new Team");
        teamDto.setDescription("Another description");

        TeamEvent teamEvent = new TeamEvent(EventType.TEAM_CREATED, teamDto);

//        companion.registerSerde(Integer.class, new ObjectMapperSerializer<TeamEvent>(), new TeamEventDeserializer());

        companion.produce(Integer.class, TeamEvent.class)
                .fromRecords(new ProducerRecord<>("team-events", 1, teamEvent))
                .awaitCompletion();

        Mockito.verify(teamConsumer, Mockito.times(1))
                        .consumeEvents(teamEvent);

        System.out.println("++++++++++++++++++ test end");
    }
}
