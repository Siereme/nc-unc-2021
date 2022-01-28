package com.example.app.repository;

import com.example.app.model.actor.Actor;
import com.example.app.model.director.Director;
import com.example.app.model.film.Film;
import com.example.app.model.genre.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DirectorsRepository extends AbstractRepository<Director> {

    public List<Director> findAll() {
        return jdbcTemplate.query("SELECT * FROM director",
                (rs, rowNum) -> new Director(rs.getInt("director_id"), rs.getString("name"), rs.getString("year")));
    }

    public List<Director> find(List<Integer> ids) {
        if (ids.size() < 1) {
            return new ArrayList<>();
        }
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query("SELECT * FROM director WHERE director_id IN (:ids)", parameters,
                (rs, rowNum) -> new Director(rs.getInt("director_id"), rs.getString("name"), rs.getString("year")));
    }

    public List<Director> findByFilms(List<Integer> ids) {
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query("SELECT director.director_id, director.name, director.year FROM director "
                        + "INNER JOIN film_director ON director.director_id=film_director.director_id "
                        + "WHERE film_director.film_id IN (:ids)", parameters,
                (rs, rowNum) -> new Director(rs.getInt("director_id"), rs.getString("name"), rs.getString("year")));
    }

    public void add(Director director) {
        jdbcTemplate.update("INSERT INTO director(name, year) VALUES(?, ?)", director.getName(), director.getYear());
        List<Integer> filmsId = director.getFilms();
        Integer directorId = jdbcTemplate.queryForObject("Select max(director_id) from director", Integer.TYPE);
        for (Integer filmId : filmsId) {
            jdbcTemplate.update("INSERT INTO film_director(film_id, director_id) values (?,?)", filmId, directorId);
        }
    }

    public void delete(int directorId) {
        jdbcTemplate.update("DELETE FROM director WHERE director_id=?", directorId);
    }

    public void edit(Director director) {
        jdbcTemplate.update("UPDATE director SET name=? WHERE director_id=?", director.getName(), director.getId());
    }

    @Override
    public List<Director> findByName(String name) {
        return jdbcTemplate.query("Select * from director where name = ?",
                ((rs, rowNum) -> new Director(rs.getInt("director_id"), rs.getString("name"), rs.getString("year"))),
                name);
    }

    public List<Film> findFilmsByDirectorId(Integer directorId) {
        return jdbcTemplate.query(
                "SELECT f.film_id as f_id, f.tittle as f_tittle, f.date as f_date FROM data_base.director d\n"
                        + "join film_director fa on fa.director_id = d.director_id\n"
                        + "join film f on f.film_id = fa.film_id\n" + "where d.director_id = ?",
                (rs, rowNum) -> new Film(rs.getInt("f_id"), rs.getString("f_tittle"), rs.getDate("f_date")),
                directorId);
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM director", Integer.class);
    }
}
