package org.geoandri.teams.producer;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.geoandri.teams.event.TeamEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TeamProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamProducer.class);

    @Channel("team-events")
    Emitter<TeamEvent> eventEmitter;

    public void publishEvent(TeamEvent teamEvent) {
        LOGGER.debug("Sending event to Kafka: {}", teamEvent);
        eventEmitter.send(teamEvent).toCompletableFuture().join();
    }
}
