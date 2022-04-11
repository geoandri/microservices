package org.geoandri.developers.consumer;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.geoandri.developers.entity.Team;
import org.geoandri.developers.event.TeamEvent;
import org.geoandri.developers.exception.EntityNotFoundException;
import org.geoandri.developers.mapper.TeamMapper;
import org.geoandri.developers.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TeamConsumer {
    private static Logger LOGGER = LoggerFactory.getLogger(TeamConsumer.class);

    @Inject
    TeamService teamService;

    @Inject
    TeamMapper teamMapper;

    @Incoming("team-events")
    public void consumeEvents(TeamEvent event) {
        LOGGER.debug("Received event from Kafka: {}", event.toString());
        switch (event.getEventType()) {
            case TEAM_CREATED: {
                teamService.save(teamMapper.toTeam(event.getTeamDto()));
                break;
            }
            case TEAM_DELETED: {
                Team team = teamMapper.toTeam(event.getTeamDto());
                try {
                    teamService.delete(team.getId());
                } catch (EntityNotFoundException e) {
                    LOGGER.warn("Team {} could not be found.", team);
                }
                break;
            }
            case TEAM_UPDATED: {
                Team team = teamMapper.toTeam(event.getTeamDto());
                try {
                    teamService.update(team);
                } catch (EntityNotFoundException e) {
                    LOGGER.warn("Team {} could not be found.", team);
                }
                break;
            }
        }
    }
}
