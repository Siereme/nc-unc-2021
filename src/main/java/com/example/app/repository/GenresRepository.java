package com.example.app.repository;

import com.example.app.model.actor.Actor;
import com.example.app.model.genre.Genre;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.sql.*;
import java.util.*;

@Repository
public class GenresRepository extends AbstractRepository<Genre> {
    private final Logger logger = Logger.getLogger(GenresRepository.class.getName());

    public List<Genre> findAll() {
        return entityManager.createNamedQuery("Genre.findAllWithFilm", Genre.class).getResultList();
    }

    public List<Genre> find(List<Integer> ids) {
        if (ids != null && ids.size() < 1) {
            return Collections.emptyList();
        }

        return entityManager.createNativeQuery("SELECT genre.genre_id, genre.tittle, film_genre.film_id "
                        + "FROM genre LEFT JOIN film_genre ON genre.genre_id=film_genre.genre_id "
                        + "WHERE genre.genre_id IN (:ids) OR COALESCE(:ids) IS NULL", Genre.class).setParameter("ids", ids)
                .getResultList();

    }

    public final class QueryRowMapper implements ResultSetExtractor<List<Genre>> {
        @Override
        public List<Genre> extractData(ResultSet rs) throws SQLException, DataAccessException {
/*            Map<Integer, Genre> genreMap = new HashMap<>();
            Genre genre;
            while (rs.next()) {
                int id = rs.getInt("genre.genre_id");
                genre = genreMap.get(id);
                if (genre == null) {
                    genre = new Genre();
                    genre.setId(id);
                    genre.setTittle(rs.getString("genre.tittle"));
                    genre.setFilms(new ArrayList<>());
                    genreMap.put(id, genre);
                }
                int filmId = rs.getInt("film_genre.film_id");
                if (filmId > 0) {
                    genre.addFilm(filmId);
                }
            }
            return new ArrayList<>(genreMap.values());*/
            return null;
        }
    }

    public List<Genre> findByTitles(List<String> titles) {
        if (titles == null || titles.size() < 1) {
            return new ArrayList<>();
        }

        return entityManager.createNativeQuery("SELECT genre_id, tittle FROM genre WHERE tittle IN (:titles)",
                Genre.class).setParameter("titles", titles).getResultList();

    }

    public List<Genre> findByFilms(List<Integer> ids) {
        if (ids == null || ids.size() < 1) {
            return new ArrayList<>();
        }

        parameters.addValue("ids", ids);
        return parameterJdbcTemplate.query("SELECT genre.genre_id, genre.tittle FROM genre "
                        + "LEFT JOIN film_genre ON genre.genre_id=film_genre.genre_id " + "WHERE film_genre.film_id IN (:ids)",
                parameters, (rs, rowNum) -> new Genre(rs.getInt("genre_id"), rs.getString("tittle")));
    }

    public void add(Genre genre) {
      /*  KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO genre(tittle) VALUES(?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, genre.getTittle());
                return ps;
            }
        }, holder);
        int genreId = Objects.requireNonNull(holder.getKey()).intValue();
        addEntitiesIds(genreId, genre.getFilms());*/
    }

    private void addEntitiesIds(int filmId, List<Integer> entityIds) {
        if (entityIds == null || entityIds.size() < 1) {
            return;
        }
        String query = "INSERT INTO film_genre(film_id, genre_id) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(query, new BatchEntitiesSetter(filmId, entityIds));
    }

    private void deleteEntitiesIds(int filmId, List<Integer> entityIds) {
        if (entityIds == null || entityIds.size() < 1) {
            return;
        }
        String query = "DELETE FROM film_genre WHERE film_id=? AND genre_id=?";
        jdbcTemplate.batchUpdate(query, new BatchEntitiesSetter(filmId, entityIds));
    }

    private final static class BatchEntitiesSetter implements BatchPreparedStatementSetter {

        private final int genreId;
        private final List<Integer> entityIds;

        public BatchEntitiesSetter(int genreId, List<Integer> entityIds) {
            this.genreId = genreId;
            this.entityIds = entityIds;
        }

        @Override
        public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, entityIds.get(i));
            ps.setInt(2, genreId);
        }

        @Override
        public int getBatchSize() {
            return entityIds.size();
        }
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

    public void edit(Genre genre) {
/*        jdbcTemplate.update("UPDATE genre set tittle=? WHERE genre_id=?", genre.getTittle(), genre.getId());

        parameters.addValue("id", genre.getId());
        String query = "SELECT film_id FROM film_genre WHERE genre_id=:id";
        List<Integer> genres = parameterJdbcTemplate.queryForList(query, parameters, Integer.class);

        List<Integer> addFilms = getEditEntitiesIds(genres, genre.getFilms());
        List<Integer> removeFilms = getEditEntitiesIds(genre.getFilms(), genres);

        deleteEntitiesIds(genre.getId(), removeFilms);
        addEntitiesIds(genre.getId(), addFilms);*/
    }

    public void delete(int genreId) {
        Genre genre = entityManager.find(Genre.class, genreId);
        entityManager.getTransaction().begin();
        entityManager.remove(genre);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Genre> findByName(String name) {
        return entityManager.createNativeQuery("Select genre_id, tittle from genre where tittle =: name", Genre.class)
                .setParameter("name", name).getResultList();
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM genre", Integer.class);
    }

    @Override
    public List<Genre> findByContains(String name) {
        return entityManager.createNativeQuery("Select * from genre where genre.tittle like :name ESCAPE '!'",
                Genre.class).setParameter("name", Collections.singletonMap("name", '%' + name + '%')).getResultList();
    }
}
