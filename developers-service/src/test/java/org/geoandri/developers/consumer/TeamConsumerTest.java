package org.geoandri.developers.consumer;

import io.quarkus.kafka.client.serialization.ObjectMapperSerializer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.ConsumerTask;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.geoandri.developers.dto.TeamDto;
import org.geoandri.developers.event.EventType;
import org.geoandri.developers.event.TeamEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        companion.registerSerde(TeamEvent.class, new TeamEventSerializer(), new TeamEventDeserializer());

        companion.produce(Integer.class, TeamEvent.class)
                .fromRecords(new ProducerRecord<>("team-events", 1, teamEvent));

        ConsumerTask<Integer, TeamEvent> consumerTask = companion.consume(Integer.class, TeamEvent.class)
                .fromTopics("team-events");
//        ConsumerRecord<Integer, TeamEvent> receivedEvent
                int size = consumerTask.awaitCompletion().getRecords().size();
//
        LOGGER.info("Receive event {}", size);

//        companion.awaitCompletion().g;



//        Mockito.verify(teamConsumer, Mockito.times(1))
//                        .consumeEvents(teamEvent);

        System.out.println("++++++++++++++++++ test end");
    }
}
