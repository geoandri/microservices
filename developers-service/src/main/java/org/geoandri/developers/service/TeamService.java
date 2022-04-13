package org.geoandri.developers.service;

import org.geoandri.developers.dao.DeveloperDao;
import org.geoandri.developers.dao.TeamDao;
import org.geoandri.developers.entity.Team;
import org.geoandri.developers.exception.EntityNotFoundException;
import org.geoandri.developers.exception.EntityPersistenceException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class TeamService {

    @Inject
    TeamDao teamDao;

    @Inject
    DeveloperDao developerDao;

    public Team save(Team team) throws EntityPersistenceException {
        return teamDao.saveTeam(team);
    }

    public Team update(Team team) throws EntityNotFoundException, EntityPersistenceException {
        return teamDao.updateTeam(team);
    }

    public void delete(long id) throws EntityNotFoundException {
        developerDao.deleteDevelopersByTeamId(id);
        teamDao.deleteTeam(id);
    }
}
