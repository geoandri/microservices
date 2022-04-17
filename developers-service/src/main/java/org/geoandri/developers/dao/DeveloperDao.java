package org.geoandri.developers.dao;

import org.eclipse.microprofile.opentracing.Traced;
import org.geoandri.developers.entity.Developer;
import org.geoandri.developers.exception.DeveloperNotFoundException;
import org.geoandri.developers.exception.EntityPersistenceException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
@Traced
public class DeveloperDao {

    @Inject
    EntityManager entityManager;

    public List<Developer> getDevelopers(int pageNum, int pageSize, long teamId) {
        pageNum = pageNum > 0 ? pageNum : 1;
        StringBuilder queryBuilder = new StringBuilder("select d from Developer d ");
        queryBuilder = teamId == 0 ? queryBuilder.append("order by d.id asc") :
                queryBuilder.append("where d.team.id = :teamId order by d.id asc");
        Query query = entityManager.createQuery(queryBuilder.toString());
        int firstResult = (pageNum - 1) * pageSize;

        if (teamId != 0) {
            query.setParameter("teamId", teamId);
        }

        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    public Developer saveDeveloper(Developer developer) {
        try {
            entityManager.persist(developer);

            return developer;
        } catch (PersistenceException e) {
            throw new EntityPersistenceException(e.getMessage());
        }

    }

    public Developer getDeveloper(long id) {
        Developer developer = entityManager.find(Developer.class, id);
        if (developer != null) {

            return developer;
        }

        throw new DeveloperNotFoundException(String.format("Developer with id %s could not be found.", id));
    }

    public Developer updateDeveloper(Developer developer) {
        Developer persistedDeveloper = getDeveloper(developer.getId());
        persistedDeveloper.setName(developer.getName());
        persistedDeveloper.setTeam(developer.getTeam());
        try {
            entityManager.merge(persistedDeveloper);

            return persistedDeveloper;
        } catch (PersistenceException e) {
            throw new EntityPersistenceException(e.getMessage());
        }
    }

    public void deleteDeveloper(long id) {
        Developer developer = getDeveloper(id);
        entityManager.remove(developer);
    }

    public void deleteDevelopersByTeamId(long id) {
        entityManager.createQuery("delete from Developer d where d.team.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
