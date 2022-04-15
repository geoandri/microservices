package org.geoandri.developers.consumer;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import org.geoandri.developers.event.TeamEvent;

public class TeamEventDeserializer extends ObjectMapperDeserializer<TeamEvent> {
    public TeamEventDeserializer() {
        super(TeamEvent.class);
    }
}
