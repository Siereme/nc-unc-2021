package com.app.repository;

import com.app.model.actor.Actor;
import com.app.model.director.Director;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class DirectorsRepository extends AbstractRepository<Director> {


    public List<Director> findAll() {
        return entityManager.createNamedQuery("Director.findAllWithFilm", Director.class).getResultList();
    }

    @Override
    public Director findById(int id) {
        return entityManager.createNamedQuery("Director.findById", Director.class).setParameter("id", id).getSingleResult();
    }

    public List<Director> find(List<Integer> ids) {
        if (ids != null && ids.size() < 1) {
            return Collections.emptyList();
        }

        return entityManager.createNamedQuery("Director.findAllWithFilmByIds")
                .setParameter("ids", ids)
                .getResultList();
    }

    public void add(Director director) {
        entityManager.persist(director);
    }

    @Override
    public void delete(int id) {
        Director director = entityManager.find(Director.class, id);
        entityManager.remove(director);
    }

    public void edit(Director director) {
        entityManager.merge(director);
    }

    @Override
    public List<Director> findByName(String name) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public List<Director> findByContains(String name) {
        return null;
    }

}
