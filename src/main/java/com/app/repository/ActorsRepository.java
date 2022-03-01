package com.app.repository;

import com.app.model.actor.Actor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.*;

@SuppressWarnings("unused")
@Repository
@Transactional
public class ActorsRepository extends AbstractRepository<Actor> {
    @Override
    public List<Actor> findAll() {
        return entityManager.createNamedQuery("Actor.findAllWithFilm", Actor.class).getResultList();
    }

    public Actor findById(int id) {
        return entityManager.createNamedQuery("Actor.findById", Actor.class).setParameter("id", id).getSingleResult();
    }

    public List<Actor> find(List<Integer> ids) {
        if (ids != null && ids.size() < 1) {
            return Collections.emptyList();
        }
        TypedQuery<Actor> query = entityManager.createNamedQuery("Actor.findAllWithFilmByIds", Actor.class).setParameter("ids", ids);
        return query.getResultList();
    }



    public void add(Actor actor) {
        entityManager.persist(actor);
    }

    @Override
    public void delete(int id) {
        Actor actor = entityManager.find(Actor.class, id);
        entityManager.remove(actor);
    }

    @Override
    public void edit(Actor actor) {
        entityManager.merge(actor);
    }

    @Override
    public List<Actor> findByName(String name) {
        TypedQuery<Actor> query = entityManager.createQuery("select distinct a from Actor a left join fetch a.films where a.name = :name", Actor.class).setParameter("name", name);
        return query.getResultList();
    }

    @SuppressWarnings("unused")
    @Override
    public int size() {
        BigInteger bigInteger =
                (BigInteger) entityManager.createNativeQuery("select count(*) from actor").getSingleResult();
        return bigInteger.intValue();
    }

    public List<Actor> findByContains(String name) {
        return entityManager.createQuery("select distinct a from Actor a left join fetch a.films where a.name like :name ESCAPE '!'",
                Actor.class).setParameter("name", '%' + name + '%').getResultList();
    }


}
