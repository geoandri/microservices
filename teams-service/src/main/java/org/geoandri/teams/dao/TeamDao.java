package org.geoandri.teams.dao;

import org.geoandri.teams.entity.Team;
import org.geoandri.teams.exception.TeamNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class TeamDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamDao.class);

    @Inject
    EntityManager entityManager;

    public List<Team> getTeams(int pageNum, int pageSize) {
        Query query = entityManager.createQuery("select t from Team t order by t.id asc");
        int firstResult = (pageNum -1) * pageSize;
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    public Team saveTeam(Team team){
        entityManager.persist(team);

        return team;
    }

    public Team getTeam(long id) throws TeamNotFoundException {
        Team team = entityManager.find(Team.class, id);
        if (team != null) {

            return team;
        }

        throw new TeamNotFoundException(String.format("Team with id %s could not be found.", id));
    }

    public Team updateTeam(long id, Team team) throws TeamNotFoundException {
        Team persistedTeam = getTeam(id);
        persistedTeam.setName(team.getName());
        persistedTeam.setDescription(team.getDescription());
        entityManager.merge(persistedTeam);

        return persistedTeam;
    }

    public void deleteTeam(long id) throws TeamNotFoundException {
        Team team = getTeam(id);
        entityManager.remove(team);
    }
}
