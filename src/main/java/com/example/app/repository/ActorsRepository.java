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
public class ActorsRepository extends AbstractRepository<Actor> {

    public List<Actor> findAll() {
        return jdbcTemplate.query("SELECT * FROM actor",
                (rs, rowNum) -> new Actor(rs.getInt("actor_id"), rs.getString("name"), rs.getString("year")));
    }

    public List<Actor> find(List<Integer> ids) {
        if (ids.size() < 1) {
            return new ArrayList<>();
        }
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query("SELECT * FROM actor WHERE actor_id IN (:ids)", parameters,
                (rs, rowNum) -> new Actor(rs.getInt("actor_id"), rs.getString("name"), rs.getString("year")));
    }

    public List<Actor> findByFilms(List<Integer> ids) {
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query("SELECT actor.actor_id, actor.name, actor.year FROM actor "
                        + "INNER JOIN film_actor ON actor.actor_id=film_actor.actor_id " + "WHERE film_actor.film_id IN (:ids)",
                parameters,
                (rs, rowNum) -> new Actor(rs.getInt("actor_id"), rs.getString("name"), rs.getString("year")));
    }

    public void add(Actor actor) {
        jdbcTemplate.update("INSERT INTO actor(name, year) VALUES(?, ?)", actor.getName(), actor.getYear());
    }

    public void delete(int actorId) {
        jdbcTemplate.update("DELETE FROM actor WHERE actor_id=?", actorId);
    }

    public void delete(List<Integer> ids) {
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        jdbcTemplate.update("DELETE FROM actor WHERE actor_id IN (:ids)", parameters);
    }

    public void edit(Actor actor) {
        jdbcTemplate.update("UPDATE actor SET name=? WHERE actor_id=?", actor.getName(), actor.getId());
    }

    @Override
    public List<Actor> findByName(String name) {
        return jdbcTemplate.query("Select * from actor where name = ?",
                ((rs, rowNum) -> new Actor(rs.getInt("actor_id"), rs.getString("name"), rs.getString("year"))));
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM actor", Integer.class);
    }
}
