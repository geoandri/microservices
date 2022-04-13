package org.geoandri.teams.service;

import org.geoandri.teams.dao.TeamDao;
import org.geoandri.teams.entity.Team;
import org.geoandri.teams.exception.TeamNotFoundException;
import org.geoandri.teams.exception.TeamPersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class TeamService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamService.class);

    @Inject
    TeamDao teamDao;

    public Team save(Team team) throws TeamPersistenceException {
        return teamDao.saveTeam(team);
    }

    public List<Team> getAll(int pageNum, int pageSize) {
        return teamDao.getTeams(pageNum, pageSize);
    }

    public Team get(long id) throws TeamNotFoundException {
        return teamDao.getTeam(id);
    }

    public Team update(long id, Team team) throws TeamNotFoundException, TeamPersistenceException {
        return teamDao.updateTeam(id, team);
    }

    public void delete(long id) throws TeamNotFoundException {
        teamDao.deleteTeam(id);
    }

}
