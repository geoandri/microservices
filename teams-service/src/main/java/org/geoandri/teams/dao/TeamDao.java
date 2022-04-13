package org.geoandri.teams.dao;

import org.geoandri.teams.entity.Team;
import org.geoandri.teams.exception.TeamNotFoundException;
import org.geoandri.teams.exception.TeamPersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class TeamDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamDao.class);

    @Inject
    EntityManager entityManager;

    public List<Team> getTeams(int pageNum, int pageSize) {
        pageNum = pageNum > 0 ? pageNum : 1;
        Query query = entityManager.createQuery("select t from Team t order by t.id asc");
        int firstResult = (pageNum -1) * pageSize;
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    public Team saveTeam(Team team) throws TeamPersistenceException {
        try {
            entityManager.persist(team);

            return team;
        }
        catch (PersistenceException e) {
            throw new TeamPersistenceException(e.getMessage());
        }
    }

    public Team getTeam(long id) throws TeamNotFoundException {
        Team team = entityManager.find(Team.class, id);
        if (team != null) {

            return team;
        }

        throw new TeamNotFoundException(String.format("Team with id %s could not be found.", id));
    }

    public Team updateTeam(long id, Team team) throws TeamNotFoundException, TeamPersistenceException {
        Team persistedTeam = getTeam(id);
        persistedTeam.setName(team.getName());
        persistedTeam.setDescription(team.getDescription());
        try {
            entityManager.merge(persistedTeam);
            entityManager.flush();

            return persistedTeam;
        }
        catch (PersistenceException e) {
            throw new TeamPersistenceException(e.getMessage());
        }
    }

    public void deleteTeam(long id) throws TeamNotFoundException {
        Team team = getTeam(id);
        entityManager.remove(team);
    }
}
