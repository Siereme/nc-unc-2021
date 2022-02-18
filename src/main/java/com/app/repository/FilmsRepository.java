package com.app.repository;

import com.app.model.film.Film;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Film app.repository
 * @see Film
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
@Transactional
@Repository
public class FilmsRepository extends AbstractRepository<Film> {

    public List<Film> findAll() {
        return entityManager.createNamedQuery("Film.findAllWithAll", Film.class).getResultList();
    }

    public Film findById(int id) {
        return entityManager.createNamedQuery("Film.findById", Film.class).setParameter("id", id).getSingleResult();
    }

    public List<Film> find(List<Integer> ids) {
        if (ids != null && ids.size() < 1) {
            return Collections.emptyList();
        }
        return null;
    }

    private final static class QueryRowMapper implements ResultSetExtractor<List<Film>> {
        @Override
        public List<Film> extractData(ResultSet rs) throws SQLException, DataAccessException {
           /* Map<Integer, Film> filmMap = new HashMap<>();
            Film film;
            while (rs.next()){
                int id = rs.getInt("film.film_id");
                film = filmMap.get(id);
                if(film == null){
                    film = new Film();
                    film.setId(id);
                    film.setTittle(rs.getString("film.tittle"));
                    film.setDate(rs.getDate("film.date"));
                    film.setGenres(new ArrayList<>());
                    film.setActors(new ArrayList<>());
                    film.setDirectors(new ArrayList<>());
                    filmMap.put(id, film);
                }
                int genreId = rs.getInt("film_genre.genre_id");
                boolean hasGenre = film.getGenres().stream().anyMatch(filmGenreId -> filmGenreId == genreId);
                if(genreId > 0 && !hasGenre){
                    film.addGenre(genreId);
                }
                int actorId = rs.getInt("film_actor.actor_id");
                boolean hasActor = film.getActors().stream().anyMatch(filmActorId -> filmActorId == actorId);
                if(actorId > 0 && !hasActor){
                    film.addActor(actorId);
                }
                int directorId = rs.getInt("film_director.director_id");
                boolean hasDirector = film.getDirectors().stream().anyMatch(filmDirectorId -> filmDirectorId == directorId);
                if(directorId > 0 && !hasDirector){
                    film.addDirector(directorId);
                }
            }
            return new ArrayList<>(filmMap.values());*/
            return null;
        }
    }

    public List<Film> findByTitles(List<String> titles) {
        return null;
    }

    public List<Film> findByGenres(List<Integer> ids) {
        return null;
    }

    public void add(Film film) {

    }

    private void addEntitiesIds(String entity, int filmId, List<Integer> entityIds) {

    }

    private void deleteEntitiesIds(String entity, int filmId, List<Integer> entityIds) {

    }

    private final static class BatchEntitiesSetter implements BatchPreparedStatementSetter {

        private final int filmId;
        private final List<Integer> entityIds;

        public BatchEntitiesSetter(int filmId, List<Integer> entityIds) {
            this.filmId = filmId;
            this.entityIds = entityIds;
        }

        @Override
        public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, filmId);
            ps.setInt(2, entityIds.get(i));
        }

        @Override
        public int getBatchSize() {
            return entityIds.size();
        }
    }

    public List<Integer> getEntitiesIds(String entity, int filmId) {
        return null;
    }

    private List<Integer> getEditEntitiesIds(List<Integer> editIds, List<Integer> ids) {
        List<Integer> editList = new ArrayList<>();
        for (int id : ids) {
            boolean isContains = editIds.stream().anyMatch(editId -> id == editId);
            if (!isContains) {
                editList.add(id);
            }
        }
        return editList;
    }

    public void edit(Film film) {

    }

    public void delete(int filmId) {
        Film film = entityManager.find(Film.class, filmId);
        entityManager.getTransaction().begin();
        entityManager.remove(film);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Film> findByName(String name) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public List<Film> findByContains(String name) {
        return null;
    }

}
