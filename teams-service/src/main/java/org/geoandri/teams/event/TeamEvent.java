package org.geoandri.teams.event;

import org.geoandri.teams.dto.TeamDto;

import java.util.Objects;

public class TeamEvent {

    private EventType eventType;
    private long teamId;

    public TeamEvent() {
    }

    public TeamEvent(EventType eventType, long teamId) {
        this.eventType = eventType;
        this.teamId = teamId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "TeamEvent{" +
                "eventType=" + eventType +
                ", teamId=" + teamId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamEvent teamEvent = (TeamEvent) o;
        return teamId == teamEvent.teamId && eventType == teamEvent.eventType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventType, teamId);
    }
}
