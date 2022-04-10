package org.geoandri.teams.event;

import org.geoandri.teams.entity.Team;

public class TeamEvent {

    private EventType eventType;
    private Team team;

    public TeamEvent(EventType eventType, Team team) {
        this.eventType = eventType;
        this.team = team;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
