package org.geoandri.developers.dao;

import org.geoandri.developers.entity.Team;
import org.geoandri.developers.exception.EntityNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Optional;

@ApplicationScoped
public class TeamDao {

    @Inject
    EntityManager entityManager;

    public Team saveTeam(Team team){
        entityManager.persist(team);

        return team;
    }

    public Team getTeam(long id) throws EntityNotFoundException {
        Team team = entityManager.find(Team.class, id);
        if (team != null) {

            return team;
        }

        throw new EntityNotFoundException(String.format("Team with id %s could not be found.", id));
    }

    public Team updateTeam(long id, Team team) throws EntityNotFoundException {
        Team persistedTeam = getTeam(id);
        persistedTeam.setName(team.getName());
        persistedTeam.setDescription(team.getDescription());
        entityManager.merge(persistedTeam);

        return persistedTeam;
    }

    public void deleteTeam(long id) throws EntityNotFoundException {
        Team team = getTeam(id);
        entityManager.remove(team);
    }

    public Team findByName(String name) throws EntityNotFoundException {
        Query query = entityManager.createQuery("select t from Team t where t.name = :name");
        query.setParameter("name", name);
        Optional<Team> team = query.getResultList().stream().findFirst();

        return team.orElseThrow(
                () -> new EntityNotFoundException(String.format("Team with name %s could not be found.", name)));
    }
}
