package org.geoandri.developers.service;

import org.eclipse.microprofile.opentracing.Traced;
import org.geoandri.developers.dao.DeveloperDao;
import org.geoandri.developers.dao.TeamDao;
import org.geoandri.developers.entity.Team;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
@Traced
public class TeamService {

    @Inject
    TeamDao teamDao;

    @Inject
    DeveloperDao developerDao;

    public Team save(Team team) {
        return teamDao.saveTeam(team);
    }

    public Team update(Team team) {
        return teamDao.updateTeam(team);
    }

    public void delete(long id) {
        developerDao.deleteDevelopersByTeamId(id);
        teamDao.deleteTeam(id);
    }
}
