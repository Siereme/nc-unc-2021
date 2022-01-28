package com.example.app.repository;

import com.example.app.model.film.Film;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/** Film app.repository
 * @see Film
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
@Repository
public class FilmsRepository extends AbstractRepository<Film> {

    public List<Film> findAll() {
        return find(null);
    }

    public List<Film> find(List<Integer> ids) {
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        return parameterJdbcTemplate.query("SELECT film.film_id, film.tittle, film.date, "
                + "film_genre.genre_id, film_actor.actor_id, film_director.director_id "
                + "FROM film "
                + "LEFT JOIN film_genre ON film.film_id=film_genre.film_id "
                + "LEFT JOIN film_actor ON film.film_id=film_actor.film_id "
                + "LEFT JOIN film_director ON film.film_id=film_director.film_id "
                + "WHERE film.film_id IN (:ids) OR COALESCE(:ids) IS NULL",
                parameters, new QueryRowMapper());
    }

    private final static class QueryRowMapper implements ResultSetExtractor<List<Film>>{
        @Override
        public List<Film> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, Film> filmMap = new HashMap<>();
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
                if(genreId > 0){
                    film.setGenre(genreId);
                }
                int actorId = rs.getInt("film_actor.actor_id");
                if(actorId > 0){
                    film.setActor(actorId);
                }
                int directorId = rs.getInt("film_director.director_id");
                if(directorId > 0){
                    film.setDirector(directorId);
                }
            }
            return new ArrayList<>(filmMap.values());
        }
    }


    public List<Film> findByTitles(List<String> titles) {
        if (titles == null || titles.size() < 1) {
            return new ArrayList<>();
        }

        SqlParameterSource parameters = new MapSqlParameterSource("titles", titles);
        List<Integer> filmIds =
                parameterJdbcTemplate.query("SELECT film_id FROM film WHERE tittle IN (:titles)", parameters,
                        (rs, rowNum) -> rs.getInt("film_id"));
        return find(filmIds);
    }

    public List<Film> findByGenres(List<Integer> ids) {
        if (ids == null || ids.size() < 1) {
            return new ArrayList<>();
        }

        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        return parameterJdbcTemplate.query("SELECT film.film_id, film.tittle FROM film "
                        + "INNER JOIN film_genre ON film.film_id=film_genre.film_id " + "WHERE film_genre.genre_id IN (:ids)",
                parameters, (rs, rowNum) -> new Film(rs.getInt("film.film_id"), rs.getString("film.tittle")));
    }

    public void add(Film film) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO film(tittle, date) VALUES(?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, film.getTittle());
                ps.setDate(2, new Date(film.getDate().getTime()));
                return ps;
            }
        }, holder);
        int filmId = Objects.requireNonNull(holder.getKey()).intValue();
        addEntitiesIds("genre", filmId, film.getGenres());
        addEntitiesIds("actor", filmId, film.getActors());
        addEntitiesIds("director", filmId, film.getDirectors());
    }
    

    private void addEntitiesIds(String entity, int filmId, List<Integer> entityIds){
        if (entityIds == null || entityIds.size() < 1) {
            return;
        }
        String query = "INSERT INTO film_" + entity + "(film_id, " + entity + "_id) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(query, new BatchEntitiesSetter(filmId, entityIds));
    }

    private void deleteEntitiesIds(String entity, int filmId, List<Integer> entityIds){
        if (entityIds == null || entityIds.size() < 1) {
            return;
        }
        String query = "DELETE FROM film_" + entity + " WHERE film_id=? AND " + entity + "_id=?";
        jdbcTemplate.batchUpdate(query, new BatchEntitiesSetter(filmId, entityIds));
    }

    private final static class BatchEntitiesSetter implements BatchPreparedStatementSetter{

        private int filmId;
        private List<Integer> entityIds;

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

    public List<Integer> getEntitiesIds(String entity, int filmId){
        SqlParameterSource parameter = new MapSqlParameterSource("id", filmId);
        String query = "SELECT " + entity + "_id FROM film_" + entity + " WHERE film_id=:id";
        return  parameterJdbcTemplate.queryForList(query, parameter, Integer.class);
    }

    private List<Integer> getEditEntitiesIds(List<Integer> editIds, List<Integer> ids) {
        List<Integer> editList = new ArrayList<>();
        for(int id : ids){
            boolean isContains = editIds.stream().anyMatch(editId -> id == editId);
            if(!isContains){
                editList.add(id);
            }
        }
        return editList;
    }

    public void edit(Film film) {
        jdbcTemplate.update("UPDATE film set tittle=?, date=? WHERE film_id=?", film.getTittle(), film.getDate(), film.getId());

        List<Integer> genres = getEntitiesIds("genre", film.getId());
        List<Integer> actors = getEntitiesIds("actor", film.getId());
        List<Integer> directors = getEntitiesIds("director", film.getId());

        List<Integer> addGenres = getEditEntitiesIds(genres, film.getGenres());
        List<Integer> addActors = getEditEntitiesIds(actors, film.getActors());
        List<Integer> addDirector = getEditEntitiesIds(directors, film.getDirectors());

        List<Integer> removeGenres = getEditEntitiesIds(film.getGenres(), genres);
        List<Integer> removeActors = getEditEntitiesIds(film.getActors(), actors);
        List<Integer> removeDirectors = getEditEntitiesIds(film.getDirectors(), directors);


        deleteEntitiesIds("genre", film.getId(), removeGenres);
        deleteEntitiesIds("actor", film.getId(), removeActors);
        deleteEntitiesIds("director", film.getId(), removeDirectors);

        addEntitiesIds("genre", film.getId(), addGenres);
        addEntitiesIds("actor", film.getId(), addActors);
        addEntitiesIds("director", film.getId(), addDirector);
    }


    public void delete(int filmId) {
        jdbcTemplate.update("DELETE FROM film WHERE film_id=?", filmId);
    }

    @Override
    public List<Film> findByName(String name) {
        return jdbcTemplate.query("Select * from film where tittle = ?",
                ((rs, rowNum) -> new Film(rs.getInt("film_id"), rs.getString("tittle"), rs.getDate("date"))));
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM film", Integer.class);
    }
}
