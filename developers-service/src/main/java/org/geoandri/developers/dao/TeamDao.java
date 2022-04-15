package org.geoandri.developers.dao;

import org.geoandri.developers.entity.Team;
import org.geoandri.developers.exception.EntityNotFoundException;
import org.geoandri.developers.exception.EntityPersistenceException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.Optional;

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

        throw new EntityNotFoundException(String.format("Team with id %s could not be found.", id));
    }

    public Team updateTeam(Team team) {
        Team persistedTeam = getTeam(team.getId());
        persistedTeam.setName(team.getName());
        persistedTeam.setDescription(team.getDescription());
        try {
            entityManager.merge(persistedTeam);
            entityManager.flush();

            return persistedTeam;
        }
        catch (PersistenceException e) {
            throw new EntityPersistenceException(e.getMessage());
        }
    }

    public void deleteTeam(long id) {
        Team team = getTeam(id);
        entityManager.remove(team);
    }

    public Team findByName(String name) {
        Query query = entityManager.createQuery("select t from Team t where t.name = :name");
        query.setParameter("name", name);
        Optional<Team> team = query.getResultList().stream().findFirst();

        return team.orElseThrow(
                () -> new EntityNotFoundException(String.format("Team with name %s could not be found.", name)));
    }
}
