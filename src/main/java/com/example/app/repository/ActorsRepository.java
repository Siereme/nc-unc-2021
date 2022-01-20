package com.example.app.repository;

import com.example.app.model.actor.Actor;
import com.example.app.model.film.Film;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ActorsRepository extends AbstractRepository<Actor> {

    public List<Actor> findAll() {
        return jdbcTemplate.query("SELECT * FROM actor",
                (rs, rowNum) -> new Actor(rs.getInt("actor_id"), rs.getString("name"), rs.getString("year")));
    }

    @Override
    public void add(Actor entity) {
        jdbcTemplate.update("INSERT INTO actor(name, year) VALUES(?, ?)", entity.getName(), entity.getYear());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM actor WHERE actor_id=?", id);
    }

    @Override
    public void edit(Actor entity) {
        jdbcTemplate.update("UPDATE film SET name=?, year=? WHERE actor_id=?", entity.getName(), entity.getYear(),
                entity.getId());
    }

    @Override
    public List<Actor> findByName(String name) {
/*        return jdbcTemplate.query("Select * from actor where name = ?", rs -> {
            Map<Integer, Actor> map = new HashMap<>();
            while (rs.next()) {
                int id = rs.getInt("actorId");
                String actorName = rs.getString("name");
                String actorYear = rs.getString("year");
                Actor actor = new Actor(id, actorName, actorYear);
                if (!map.containsKey(actor.getId())) {
                    map.put(actor.getId(), actor);
                }
            }
            return new ArrayList<>(map.values());
        }, name);*/

        return jdbcTemplate.query("Select * from actor where name = ?",
                (rs, rowNum) -> new Actor(rs.getInt("actor_id"), rs.getString("name"), rs.getString("year")), name);

    }

    public List<Actor> find(List<Integer> ids) {
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query("SELECT * FROM actor WHERE actorId IN (:ids)",
                parameters,
                (rs, rowNum) -> new Actor(
                        rs.getInt("actorId"),
                        rs.getString("name")
                )
        );
    }

    public List<Actor> findByFilms(List<Integer> ids){
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query(
                "SELECT actor.actorId, actor.name, actor.year FROM actor " +
                        "INNER JOIN filmactor ON actor.actorId=filmactor.actorId " +
                        "WHERE filmactor.filmId IN (:ids)",
                parameters,
                (rs, rowNum) -> new Actor(
                        rs.getInt("actorId"),
                        rs.getString("name"),
                        rs.getString("year")
                )
        );
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM actor", Integer.class);
    }
}
