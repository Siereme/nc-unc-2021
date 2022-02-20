package com.app.repository;

import com.app.model.actor.Actor;
import com.app.model.film.Film;
import com.app.model.genre.Genre;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        return entityManager.createNamedQuery("Actor.findAllWithFilmByIds")
                            .setParameter("ids", ids)
                            .getResultList();

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
        return entityManager.createQuery("select a from Actor a where a.name = :name").setParameter("name", name)
                .getResultList();
    }

    @Override
    public int size() {
        BigInteger bigInteger =
                (BigInteger) entityManager.createNativeQuery("select count(*) from actor").getSingleResult();
        return bigInteger.intValue();
    }

    //fixme ;(
    public List<Actor> findByContains(String name) {
        return entityManager.createQuery("select a from Actor a join fetch a.films where a.name like :name ESCAPE '!'",
                Actor.class).setParameter("name", '%' + name + '%').getResultList();
    }


}
