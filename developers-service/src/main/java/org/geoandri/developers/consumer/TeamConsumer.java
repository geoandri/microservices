package org.geoandri.developers.consumer;

import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.geoandri.developers.annotation.KafkaConsumerInterceptor;
import org.geoandri.developers.entity.Team;
import org.geoandri.developers.event.TeamEvent;
import org.geoandri.developers.exception.EntityPersistenceException;
import org.geoandri.developers.exception.TeamNotFoundException;
import org.geoandri.developers.mapper.TeamMapper;
import org.geoandri.developers.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class TeamConsumer {
    private static Logger LOGGER = LoggerFactory.getLogger(TeamConsumer.class);

    @Inject
    TeamService teamService;

    @Inject
    TeamMapper teamMapper;

    @Incoming("team-events")
    @Blocking
    @Traced
    @KafkaConsumerInterceptor
    public CompletionStage<Void> consumeEvents(Message<TeamEvent> message) {
        LOGGER.debug("Received event from Kafka: {}", message.getPayload().toString());
        TeamEvent event = message.getPayload();
        switch (event.getEventType()) {
            case TEAM_CREATED: {
                try {
                    teamService.save(teamMapper.toTeam(event.getTeamDto()));
                } catch (EntityPersistenceException e) {
                    LOGGER.warn("Error while persisting team {}. The error was: {}", event.getTeamDto(), e.getMessage());
                }
                break;
            }
            case TEAM_DELETED: {
                Team team = teamMapper.toTeam(event.getTeamDto());
                try {
                    teamService.delete(team.getId());
                } catch (TeamNotFoundException e) {
                    LOGGER.warn("Team {} could not be found.", team);
                }
                break;
            }
            case TEAM_UPDATED: {
                Team team = teamMapper.toTeam(event.getTeamDto());
                try {
                    teamService.update(team);
                } catch (TeamNotFoundException e) {
                    LOGGER.warn("Team {} could not be found.", team);
                } catch (EntityPersistenceException e) {
                    LOGGER.error(e.getMessage());
                }
                break;
            }
        }
        return message.ack();
    }
}
