package org.geoandri.teams.producer;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.geoandri.teams.event.TeamEvent;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TeamProducer {

    @Channel("team-events")
    Emitter<TeamEvent> eventEmitter;

    public void publishEvent(TeamEvent teamEvent) {
        eventEmitter.send(teamEvent).toCompletableFuture().join();
    }
}
