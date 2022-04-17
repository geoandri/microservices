package org.geoandri.developers.dao;

import org.geoandri.developers.entity.Team;
import org.geoandri.developers.exception.EntityPersistenceException;
import org.geoandri.developers.exception.TeamNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

@ApplicationScoped
public class TeamDao {

    @Inject
    EntityManager entityManager;

    public Team saveTeam(Team team) {
        try {
            entityManager.persist(team);

            return team;
        }
        catch (PersistenceException e) {
            throw new EntityPersistenceException(e.getMessage());
        }
    }

    public Team getTeam(long id) {
        Team team = entityManager.find(Team.class, id);
        if (team != null) {

            return team;
        }

        throw new TeamNotFoundException(String.format("Team with id %s could not be found.", id));
    }

    public void deleteTeam(long id) {
        Team team = getTeam(id);
        entityManager.remove(team);
    }
}
