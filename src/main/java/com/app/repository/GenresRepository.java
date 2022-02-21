package com.app.repository;

import com.app.model.genre.Genre;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.*;

@Repository
public class GenresRepository extends AbstractRepository<Genre> {
    private static final Logger logger = Logger.getLogger(GenresRepository.class);

    public List<Genre> findAll() {
        return entityManager.createNamedQuery("Genre.findAllWithFilm", Genre.class).getResultList();
    }

    @Override
    public Genre findById(int id) {
        return entityManager.createNamedQuery("Genre.findById", Genre.class).setParameter("id", id).getSingleResult();
    }

    public List<Genre> find(List<Integer> ids) {
        if (ids != null && ids.size() < 1) {
            return Collections.emptyList();
        }

        return entityManager.createNamedQuery("Genre.findAllWithFilmByIds").setParameter("ids", ids).getResultList();

    }

    public List<Genre> findByTitles(List<String> titles) {
        if (titles == null || titles.size() < 1) {
            return new ArrayList<>();
        }

        return entityManager.createNativeQuery("SELECT genre_id, tittle FROM genre WHERE tittle IN (:titles)",
                Genre.class).setParameter("titles", titles).getResultList();

    }

    public void add(Genre genre) {
        entityManager.persist(genre);
    }

    @Override
    public void delete(int id) {
        Genre genre = entityManager.find(Genre.class, id);
        entityManager.remove(genre);
    }

    public void edit(Genre genre) {
        entityManager.merge(genre);
    }

    @Override
    public List<Genre> findByName(String name) {
        return entityManager.createNativeQuery("Select genre_id, tittle from genre where tittle =: name", Genre.class)
                .setParameter("name", name).getResultList();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public List<Genre> findByContains(String name) {
        return entityManager.createQuery(
                "select distinct g from Genre g left join fetch g.films where g.tittle like :name ESCAPE '!'",
                Genre.class).setParameter("name", '%' + name + '%').getResultList();
    }
}
