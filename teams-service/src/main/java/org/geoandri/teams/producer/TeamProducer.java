package org.geoandri.teams.producer;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.geoandri.teams.event.TeamEvent;

public class TeamProducer {

    @Channel("team-events")
    Emitter<TeamEvent> eventEmitter;
}
