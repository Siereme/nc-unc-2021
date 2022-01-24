package com.example.app.repository;

import com.example.app.model.actor.Actor;
import com.example.app.model.film.Film;
import com.example.app.model.genre.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class GenresRepository extends AbstractRepository<Genre> {

    public List<Genre> findAll() {
        return jdbcTemplate.query(
                "SELECT " + "genre.genre_id, genre.tittle, " + "GROUP_CONCAT(distinct(film_genre.film_id)) AS filmIds "
                        + "FROM genre " + "LEFT JOIN film_genre ON genre.genre_id=film_genre.genre_id "
                        + "GROUP BY genre.genre_id", queryRowMapper());
    }

    public List<Genre> find(List<Integer> ids) {
        if (ids == null || ids.size() < 1) {
            return new ArrayList<>();
        }

        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query(
                "SELECT " + "genre.genre_id, genre.tittle, " + "GROUP_CONCAT(distinct(film_genre.film_id)) AS filmIds "
                        + "FROM genre " + "LEFT JOIN film_genre ON genre.genre_id=film_genre.genre_id "
                        + "WHERE genre.genre_id IN (:ids) " + "GROUP BY genre.genre_id", parameters, queryRowMapper());
    }

    private ResultSetExtractor<List<Genre>> queryRowMapper() {
        return new ResultSetExtractor<List<Genre>>() {
            public List<Genre> extractData(ResultSet rs) throws SQLException {
                List<Genre> genres = new ArrayList<>();
                while (rs.next()) {
                    Genre genre = new Genre();
                    genre.setId(rs.getInt("genre.genre_id"));
                    genre.setTittle(rs.getString("genre.tittle"));
                    String film = rs.getString("filmIds");
                    if (film != null && film.length() > 0) {
                        String[] films = film.split(",");
                        if (films.length > 0) {
                            List<Integer> filmList =
                                    Arrays.stream(films).map(Integer::parseInt).collect(Collectors.toList());
                            genre.setFilms(filmList);
                        }
                    }
                    genres.add(genre);
                }
                return genres;
            }
        };
    }

    public List<Genre> findByTitles(List<String> titles) {
        if (titles == null || titles.size() < 1) {
            return new ArrayList<>();
        }

        SqlParameterSource parameters = new MapSqlParameterSource("titles", titles);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        List<Integer> genreIds =
                parameterJdbcTemplate.query("SELECT genre_id FROM genre WHERE tittle IN (:titles)", parameters,
                        (rs, rowNum) -> rs.getInt("genre_id"));
        return find(genreIds);
    }

    public List<Genre> findByFilms(List<Integer> ids) {
        if (ids == null || ids.size() < 1) {
            return new ArrayList<>();
        }

        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query("SELECT genre.genre_id, genre.tittle FROM genre "
                        + "LEFT JOIN film_genre ON genre.genre_id=film_genre.genre_id " + "WHERE film_genre.film_id IN (:ids)",
                parameters, (rs, rowNum) -> new Genre(rs.getInt("genre_id"), rs.getString("tittle")));
    }

    public void add(Genre genre) {
        KeyHolder holder = new GeneratedKeyHolder();
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
        addToDependentTable("film_genre", genreId, genre.getFilms());
    }

    public void addToDependentTable(String table, int genreId, List<Integer> entityIds) {
        if (entityIds == null || entityIds.size() < 1) {
            return;
        }

        jdbcTemplate.batchUpdate("INSERT INTO " + table + " VALUES (?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, entityIds.get(i));
                ps.setInt(2, genreId);
            }

            @Override
            public int getBatchSize() {
                return entityIds.size();
            }
        });
    }

    public void delete(int genreId) {
        jdbcTemplate.update("DELETE FROM genre WHERE genre_id=?", genreId);
    }

    public void edit(Genre genre) {
        delete(genre.getId());
        add(genre);
    }

    @Override
    public List<Genre> findByName(String name) {
        return jdbcTemplate.query("Select * from genre where tittle = ?",
                ((rs, rowNum) -> new Genre(rs.getInt("genre_id"), rs.getString("tittle"))));
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM genre", Integer.class);
    }
}
