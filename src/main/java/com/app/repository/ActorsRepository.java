package com.app.repository;

import com.app.model.actor.Actor;
import com.app.model.film.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ActorsRepository extends AbstractRepository<Actor> {
    @Override
    public List<Actor> findAll() {
        return entityManager.createNamedQuery("Actor.findAllWithFilm", Actor.class).getResultList();
    }

    public List<Actor> find(List<Integer> ids) {
        return null;
    }

    public List<Actor> findByFilms(List<Integer> ids) {
        return null;
    }

    public void add(Actor actor) {

    }

    public void delete(int actorId) {
        Actor actor = entityManager.find(Actor.class, actorId);
        entityManager.getTransaction().begin();
        entityManager.remove(actor);
        entityManager.getTransaction().commit();
    }

    public void delete(List<Integer> ids) {
    }

    public void edit(Actor actor) {

    }

    @Override
    public List<Actor> findByName(String name) {
        return null;
    }

    public List<Film> findFilmsByActorId(Integer actorId) {
        return entityManager.createNativeQuery(
                "SELECT f.film_id as film_id, f.tittle as tittle, f.date as date FROM data_base.actor a\n"
                        + "join film_actor fa on fa.actor_id = a.actor_id\n" + "join film f on f.film_id = fa.film_id\n"
                        + "where a.actor_id = :actorId", Film.class).setParameter("actorId", actorId).getResultList();
    }

    @Override
    public int size() {
        return 0;
    }

    //fixme ;(
    public List<Actor> findByContains(String name) {
        return entityManager.createQuery("select a from Actor a join fetch a.films where a.name like :name ESCAPE '!'", Actor.class)
                .setParameter("name",Collections.singletonMap("name", '%' + name + '%'))
                .getResultList();
    }

    @Autowired
    FilmsRepository filmsRepository;

}
