package org.geoandri.developers.service;

import org.geoandri.developers.dao.DeveloperDao;
import org.geoandri.developers.dao.TeamDao;
import org.geoandri.developers.entity.Developer;
import org.geoandri.developers.entity.Team;
import org.geoandri.developers.exception.EntityNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class DeveloperService {

    @Inject
    DeveloperDao developerDao;

    @Inject
    TeamDao teamDao;

    public Developer save(Developer developer) throws EntityNotFoundException {
            Team team = teamDao.findByName(developer.getTeam().getName());
            developer.setTeam(team);

            return developerDao.saveDeveloper(developer);
    }

    public List<Developer> getAll(int pageNum, int pageSize, long teamId) {
        return developerDao.getDevelopers(pageNum,pageSize, teamId);
    }

    public Developer get(long id) throws EntityNotFoundException {
        return developerDao.getDeveloper(id);
    }

    public Developer update(Developer developer) throws EntityNotFoundException {
        Team team = teamDao.findByName(developer.getTeam().getName());
        developer.setTeam(team);

        return developerDao.updateDeveloper(developer);
    }

    public void delete(long id) throws EntityNotFoundException {
        developerDao.deleteDeveloper(id);
    }
}
