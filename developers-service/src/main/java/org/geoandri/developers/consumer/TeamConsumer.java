package org.geoandri.developers.consumer;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.geoandri.developers.event.TeamEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TeamConsumer {
    private static Logger LOGGER = LoggerFactory.getLogger(TeamConsumer.class);

    @Incoming("team-events")
    public void logEvents(TeamEvent event) {
        LOGGER.info(event.toString());
    }
}
