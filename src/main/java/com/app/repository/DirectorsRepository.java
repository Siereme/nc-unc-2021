package com.app.repository;

import com.app.model.director.Director;
import com.app.model.film.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class DirectorsRepository extends AbstractRepository<Director> {

    @Autowired
    FilmsRepository filmsRepository;

    public List<Director> findAll() {
        return entityManager.createNamedQuery("Director.findAllWithFilm", Director.class).getResultList();
    }

    public List<Director> find(List<Integer> ids) {
        return null;
    }

    public List<Director> findByFilms(List<Integer> ids) {
        return null;
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

    }

    @Override
    public List<Director> findByName(String name) {
        return null;
    }

    public List<Film> findFilmsByDirectorId(Integer directorId) {
        return entityManager.createNativeQuery(
                        "SELECT f.film_id as film_id, f.tittle as tittle, f.date as date FROM data_base.director d\n"
                                + "join film_director fa on fa.director_id = d.director_id\n"
                                + "join film f on f.film_id = fa.film_id\n" + "where d.director_id = :directorId", Film.class)
                .setParameter("directorId", directorId).getResultList();
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
