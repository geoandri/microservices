package org.geoandri.developers.event;

import org.geoandri.developers.dto.TeamDto;

public class TeamEvent {
    private EventType eventType;
    private TeamDto teamDto;

    public TeamEvent() {
    }

    public TeamEvent(EventType eventType, TeamDto teamDto) {
        this.eventType = eventType;
        this.teamDto = teamDto;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public TeamDto getTeamDto() {
        return teamDto;
    }

    public void setTeamDto(TeamDto teamDto) {
        this.teamDto = teamDto;
    }

    @Override
    public String toString() {
        return "TeamEvent{" +
                "eventType=" + eventType +
                ", teamDto=" + teamDto +
                '}';
    }
}
