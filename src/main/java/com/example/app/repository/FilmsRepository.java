package com.example.app.repository;
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
                        "LEFT JOIN filmgenre ON film.filmId=filmgenre.filmId " +
                        "LEFT JOIN filmactor ON film.filmId=filmactor.filmId " +
                        "LEFT JOIN filmdirector ON film.filmId=filmdirector.filmId " +
                        "GROUP BY film.filmId",
                queryRowMapper()
        );
    }

    public List<Film> find(List<Integer> ids) {
        if(ids == null || ids.size() < 1) return new ArrayList<>();

        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query(
                "SELECT " +
                        "film.filmId, film.tittle, film.date, " +
                        "GROUP_CONCAT(distinct(filmgenre.genreId)) AS genreIds, " +
                        "GROUP_CONCAT(distinct(filmactor.actorId)) AS actorIds, " +
                        "GROUP_CONCAT(distinct(filmdirector.directorId)) AS directorIds " +
                        "FROM film " +
                        "LEFT JOIN filmgenre ON film.filmId=filmgenre.filmId " +
                        "LEFT JOIN filmactor ON film.filmId=filmactor.filmId " +
                        "LEFT JOIN filmdirector ON film.filmId=filmdirector.filmId " +
                        "WHERE film.filmId IN (:ids) " +
                        "GROUP BY film.filmId",
                parameters,
                queryRowMapper()
        );
    }

    private ResultSetExtractor<List<Film>> queryRowMapper(){
        return new ResultSetExtractor<List<Film>>() {
            public List<Film> extractData(ResultSet rs) throws SQLException {
                List<Film> films = new ArrayList<>();
                while (rs.next()){
                    Film film = new Film();
                    film.setId(rs.getInt("film.filmId"));
                    film.setTittle(rs.getString("film.tittle"));
                    film.setDate(rs.getDate("film.date"));
                    String genre = rs.getString("genreIds");
                    if(genre != null && genre.length() > 0){
                        String[] genres = genre.split(",");
                        if(genres.length > 0){
                            List<Integer> genreList = Arrays.stream(genres).map(Integer::parseInt).collect(Collectors.toList());
                            film.setGenres(genreList);
                        }
                    }
                    String actor = rs.getString("actorIds");
                    if(actor != null && actor.length() > 0){
                        String[] actors = actor.split(",");
                        if(actors.length > 0){
                            List<Integer> actorsList = Arrays.stream(actors).map(Integer::parseInt).collect(Collectors.toList());
                            film.setActors(actorsList);
                        }
                    }
                    String director = rs.getString("directorIds");
                    if(director != null && director.length() > 0){
                        String[] directors = director.split(",");
                        if(directors.length > 0){
                            List<Integer> directorList = Arrays.stream(directors).map(Integer::parseInt).collect(Collectors.toList());
                            film.setDirectors(directorList);
                        }
                    }
                    films.add(film);
                }
                return films;
            }
        };
    }

    public List<Film> findByTitles(List<String> titles){
        if(titles == null || titles.size() < 1) return new ArrayList<>();

        SqlParameterSource parameters = new MapSqlParameterSource("titles", titles);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        List<Integer> filmIds = parameterJdbcTemplate.query(
                "SELECT filmId FROM film WHERE tittle IN (:titles)",
                parameters, (rs, rowNum) -> rs.getInt("filmId")
        );
        return find(filmIds);
    }

    public List<Film> findByGenres(List<Integer> ids){
        if(ids == null || ids.size() < 1) return new ArrayList<>();

        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query(
                "SELECT film.filmId, film.tittle FROM film " +
                        "INNER JOIN filmgenre ON film.filmId=filmgenre.filmId " +
                        "WHERE filmgenre.genreId IN (:ids)",
                parameters,
                (rs, rowNum) -> new Film(
                        rs.getInt("film.filmId"),
                        rs.getString("film.tittle")
                )
        );
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
        if(entityIds == null || entityIds.size() < 1) return;

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
