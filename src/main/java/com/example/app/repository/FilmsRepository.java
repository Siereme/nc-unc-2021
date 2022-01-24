package com.example.app.repository;

import com.example.app.model.actor.Actor;
import com.example.app.model.film.Film;
import com.example.app.model.genre.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

/** Film app.repository
 * @see Film
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
@Repository
public class FilmsRepository extends AbstractRepository<Film> {

    public List<Film> findAll() {
        return jdbcTemplate.query("SELECT " + "film.film_id, film.tittle, film.date, "
                        + "GROUP_CONCAT(distinct(film_genre.genre_id)) AS genreIds, "
                        + "GROUP_CONCAT(distinct(film_actor.actor_id)) AS actorIds, "
                        + "GROUP_CONCAT(distinct(film_director.director_id)) AS directorIds " + "FROM film "
                        + "LEFT JOIN film_genre ON film.film_id=film_genre.film_id "
                        + "LEFT JOIN film_actor ON film.film_id=film_actor.film_id "
                        + "LEFT JOIN film_director ON film.film_id=film_director.film_id " + "GROUP BY film.film_id",
                queryRowMapper());
    }

    public List<Film> find(List<Integer> ids) {
        if (ids == null || ids.size() < 1) {
            return new ArrayList<>();
        }

        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query("SELECT " + "film.film_id, film.tittle, film.date, "
                + "GROUP_CONCAT(distinct(film_genre.genre_id)) AS genreIds, "
                + "GROUP_CONCAT(distinct(film_actor.actor_id)) AS actorIds, "
                + "GROUP_CONCAT(distinct(film_director.director_id)) AS directorIds " + "FROM film "
                + "LEFT JOIN film_genre ON film.film_id=film_genre.film_id "
                + "LEFT JOIN film_actor ON film.film_id=film_actor.film_id "
                + "LEFT JOIN film_director ON film.film_id=film_director.film_id " + "WHERE film.film_id IN (:ids) "
                + "GROUP BY film.film_id", parameters, queryRowMapper());
    }

    private ResultSetExtractor<List<Film>> queryRowMapper() {
        return new ResultSetExtractor<List<Film>>() {
            public List<Film> extractData(ResultSet rs) throws SQLException {
                List<Film> films = new ArrayList<>();
                while (rs.next()) {
                    Film film = new Film();
                    film.setId(rs.getInt("film.film_id"));
                    film.setTittle(rs.getString("film.tittle"));
                    film.setDate(rs.getDate("film.date"));
                    String genre = rs.getString("genreIds");
                    if (genre != null && genre.length() > 0) {
                        String[] genres = genre.split(",");
                        if (genres.length > 0) {
                            List<Integer> genreList =
                                    Arrays.stream(genres).map(Integer::parseInt).collect(Collectors.toList());
                            film.setGenres(genreList);
                        }
                    }
                    String actor = rs.getString("actorIds");
                    if (actor != null && actor.length() > 0) {
                        String[] actors = actor.split(",");
                        if (actors.length > 0) {
                            List<Integer> actorsList =
                                    Arrays.stream(actors).map(Integer::parseInt).collect(Collectors.toList());
                            film.setActors(actorsList);
                        }
                    }
                    String director = rs.getString("directorIds");
                    if (director != null && director.length() > 0) {
                        String[] directors = director.split(",");
                        if (directors.length > 0) {
                            List<Integer> directorList =
                                    Arrays.stream(directors).map(Integer::parseInt).collect(Collectors.toList());
                            film.setDirectors(directorList);
                        }
                    }
                    films.add(film);
                }
                return films;
            }
        };
    }

    public List<Film> findByTitles(List<String> titles) {
        if (titles == null || titles.size() < 1) {
            return new ArrayList<>();
        }

        SqlParameterSource parameters = new MapSqlParameterSource("titles", titles);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
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
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
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
        addToDependentTable("film_genre", filmId, film.getGenres());
        addToDependentTable("film_actor", filmId, film.getActors());
        addToDependentTable("film_director", filmId, film.getDirectors());
    }

    public void addToDependentTable(String table, int filmId, List<Integer> entityIds) {
        if (entityIds == null || entityIds.size() < 1) {
            return;
        }

        jdbcTemplate.batchUpdate("INSERT INTO " + table + " VALUES (?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, filmId);
                ps.setInt(2, entityIds.get(i));
            }

            @Override
            public int getBatchSize() {
                return entityIds.size();
            }
        });
    }

    public void delete(int filmId) {
        jdbcTemplate.update("DELETE FROM film WHERE film_id=?", filmId);
    }

    public void edit(Film film) {
        delete(film.getId());
        add(film);
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
