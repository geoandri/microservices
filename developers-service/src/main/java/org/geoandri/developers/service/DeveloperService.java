package org.geoandri.developers.service;

import org.eclipse.microprofile.opentracing.Traced;
import org.geoandri.developers.dao.DeveloperDao;
import org.geoandri.developers.dao.TeamDao;
import org.geoandri.developers.entity.Developer;
import org.geoandri.developers.entity.Team;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
@Traced
public class DeveloperService {

    @Inject
    DeveloperDao developerDao;

    @Inject
    TeamDao teamDao;

    public Developer save(Developer developer) {
        Team team = teamDao.getTeam(developer.getTeam().getId());
        developer.setTeam(team);

        return developerDao.saveDeveloper(developer);
    }

    public List<Developer> getAll(int pageNum, int pageSize, long teamId) {
        return developerDao.getDevelopers(pageNum, pageSize, teamId);
    }

    public Developer get(long id) {
        return developerDao.getDeveloper(id);
    }

    public Developer update(Developer developer) {
        Team team = teamDao.getTeam(developer.getTeam().getId());
        developer.setTeam(team);

        return developerDao.updateDeveloper(developer);
    }

    public void delete(long id) {
        developerDao.deleteDeveloper(id);
    }
}
