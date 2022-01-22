package com.example.app.repository;
import com.example.app.model.film.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
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
public class FilmsRepository implements IRepository<Film> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Film> findAll() {
        return jdbcTemplate.query(
                "SELECT " +
                        "film.filmId, film.tittle, film.date, " +
                        "GROUP_CONCAT(distinct(filmgenre.genreId)) AS genreIds, " +
                        "GROUP_CONCAT(distinct(filmactor.actorId)) AS actorIds, " +
                        "GROUP_CONCAT(distinct(filmdirector.directorId)) AS directorIds " +
                        "FROM film " +
                        "INNER JOIN filmgenre ON film.filmId=filmgenre.filmId " +
                        "INNER JOIN filmactor ON film.filmId=filmactor.filmId " +
                        "INNER JOIN filmdirector ON film.filmId=filmdirector.filmId " +
                        "GROUP BY film.filmId",
                (rs, rowNum) -> {
                    Film film = new Film();
                    film.setId(rs.getInt("film.filmId"));
                    String title = rs.getString("film.tittle");
                    film.setTittle(title);
                    film.setDate(rs.getDate("film.date"));
                    String[] genres = rs.getString("genreIds").split(",");
                    if(genres.length > 0){
                        List<Integer> genreList = Arrays.stream(genres).map(Integer::parseInt).collect(Collectors.toList());
                        film.setGenres(genreList);
                    }
                    String[] actors = rs.getString("actorIds").split(",");
                    if(actors.length > 0){
                        List<Integer> actorsList = Arrays.stream(actors).map(Integer::parseInt).collect(Collectors.toList());
                        film.setActors(actorsList);
                    }
                    String[] directors = rs.getString("directorIds").split(",");
                    if(directors.length > 0){
                        List<Integer> directorList = Arrays.stream(directors).map(Integer::parseInt).collect(Collectors.toList());
                        film.setDirectors(directorList);
                    }
                    return film;
                }
        );
    }

    public List<Film> find(List<Integer> ids) {
        if(ids.size() < 1) return new ArrayList<>();
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query(
                "SELECT " +
                        "film.filmId, film.tittle, film.date, " +
                        "GROUP_CONCAT(distinct(filmgenre.genreId)) AS genreIds, " +
                        "GROUP_CONCAT(distinct(filmactor.actorId)) AS actorIds, " +
                        "GROUP_CONCAT(distinct(filmdirector.directorId)) AS directorIds " +
                        "FROM film " +
                        "INNER JOIN filmgenre ON film.filmId=filmgenre.filmId " +
                        "INNER JOIN filmactor ON film.filmId=filmactor.filmId " +
                        "INNER JOIN filmdirector ON film.filmId=filmdirector.filmId " +
                        "WHERE film.filmId IN (:ids)" +
                        "GROUP BY film.filmId",
                parameters,
                (rs, rowNum) -> {
                    Film film = new Film();
                    film.setId(rs.getInt("film.filmId"));
                    String title = rs.getString("film.tittle");
                    film.setTittle(title);
                    film.setDate(rs.getDate("film.date"));
                    String[] genres = rs.getString("genreIds").split(",");
                    if(genres.length > 0){
                        List<Integer> genreList = Arrays.stream(genres).map(Integer::parseInt).collect(Collectors.toList());
                        film.setGenres(genreList);
                    }
                    String[] actors = rs.getString("actorIds").split(",");
                    if(actors.length > 0){
                        List<Integer> actorsList = Arrays.stream(actors).map(Integer::parseInt).collect(Collectors.toList());
                        film.setActors(actorsList);
                    }
                    String[] directors = rs.getString("directorIds").split(",");
                    if(directors.length > 0){
                        List<Integer> directorList = Arrays.stream(directors).map(Integer::parseInt).collect(Collectors.toList());
                        film.setDirectors(directorList);
                    }
                    return film;
                }
        );
    }

    public List<Film> findByTitles(List<String> titles){
        if(titles.size() < 1) return new ArrayList<>();
        SqlParameterSource parameters = new MapSqlParameterSource("titles", titles);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        List<Integer> filmIds = parameterJdbcTemplate.query(
                "SELECT filmId FROM film WHERE tittle IN (:titles)",
                parameters, (rs, rowNum) -> rs.getInt("filmId")
        );
        return find(filmIds);
    }


    public List<Film> findByFilms(List<Integer> id) {
        return null;
    }

    public void add(Film film){
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO film(tittle, date) VALUES(?, ?)"
                        , Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, film.getTittle());
                ps.setDate(2, new Date(film.getDate().getTime()));
                return ps;
            }
        }, holder);
        int filmId = Objects.requireNonNull(holder.getKey()).intValue();
        addToDependentTable("filmgenre", filmId, film.getGenres());
        addToDependentTable("filmactor", filmId, film.getActors());
        addToDependentTable("filmdirector", filmId, film.getDirectors());
    }

    public void addToDependentTable(String table, int filmId, List<Integer> entityIds){
        jdbcTemplate.batchUpdate("INSERT INTO " + table + " VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
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

    public void delete(int filmId){
        jdbcTemplate.update(
                "DELETE FROM film WHERE filmId=?",
                filmId
        );
    }


    public void edit(Film film) {
        delete(film.getId());
        add(film);
    }

    @Override
    public int size() {
        return 0;
    }
}
