package com.example.app.repository;

import com.example.app.model.actor.Actor;
import com.example.app.model.film.Film;
import com.example.app.model.genre.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ActorsRepository extends AbstractRepository<Actor> {
    @Override
    public List<Actor> findAll() {
        return jdbcTemplate.query("SELECT * FROM actor",
                (rs, rowNum) -> new Actor(rs.getInt("actor_id"), rs.getString("name"), rs.getString("year")));
    }

    public List<Actor> find(List<Integer> ids) {
        if (ids.size() < 1) {
            return new ArrayList<>();
        }
        parameters.addValue("ids", ids);
        return parameterJdbcTemplate.query("SELECT * FROM actor WHERE actor_id IN (:ids)", parameters,
                (rs, rowNum) -> new Actor(rs.getInt("actor_id"), rs.getString("name"), rs.getString("year")));
    }

    public List<Actor> findByFilms(List<Integer> ids) {
        parameters.addValue("ids", ids);
        return parameterJdbcTemplate.query("SELECT actor.actor_id, actor.name, actor.year FROM actor "
                        + "INNER JOIN film_actor ON actor.actor_id=film_actor.actor_id " + "WHERE film_actor.film_id IN (:ids)",
                parameters,
                (rs, rowNum) -> new Actor(rs.getInt("actor_id"), rs.getString("name"), rs.getString("year")));
    }

    public void add(Actor actor) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO actor(name, year) VALUES(?, ?)", actor.getName(), actor.getYear(), holder);
        List<Integer> filmsId = actor.getFilms();
        Integer actorId = Objects.requireNonNull(holder.getKey()).intValue();
        for (Integer filmId : filmsId) {
            jdbcTemplate.update("INSERT INTO film_actor(film_id, actor_id) values (?,?)", filmId, actorId);
        }
    }

    public void delete(int actorId) {
        jdbcTemplate.update("DELETE FROM actor WHERE actor_id=?", actorId);
    }

    public void delete(List<Integer> ids) {
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        jdbcTemplate.update("DELETE FROM actor WHERE actor_id IN (:ids)", parameters);
    }

    public void edit(Actor actor) {
        String newName = actor.getName();
        String newYear = actor.getYear();
        Integer actorId = actor.getId();
        jdbcTemplate.update("UPDATE actor SET name = ?, year = ? WHERE actor_id=?", newName, newYear, actorId);
        List<Integer> filmListToDelete = filmsRepository.findFilmsToDelete(actor, "film_actor", "actor_id");
        List<Integer> filmListToAdd = filmsRepository.findFilmsToAdd(actor, "film_actor", "actor_id");
        for (Integer filmId : filmListToDelete) {
            jdbcTemplate.update("delete from film_actor where actor_id = ? and film_id = ?", actorId, filmId);
        }

        for (Integer filmId : filmListToAdd) {
            jdbcTemplate.update("insert into film_actor(film_id, actor_id) values (?,?)", filmId, actorId);
        }

    }

    @Override
    public List<Actor> findByName(String name) {
        return jdbcTemplate.query("Select * from actor where name = ?",
                ((rs, rowNum) -> new Actor(rs.getInt("actor_id"), rs.getString("name"), rs.getString("year"))), name);
    }

    public List<Film> findFilmsByActorId(Integer actorId) {
        return jdbcTemplate.query(
                "SELECT f.film_id as f_id, f.tittle as f_tittle, f.date as f_date FROM data_base.actor a\n"
                        + "join film_actor fa on fa.actor_id = a.actor_id\n" + "join film f on f.film_id = fa.film_id\n"
                        + "where a.actor_id = ?",
                (rs, rowNum) -> new Film(rs.getInt("f_id"), rs.getString("f_tittle"), rs.getDate("f_date")), actorId);
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM actor", Integer.class);
    }

    public List<Actor> findByContains(String name) {
        return jdbcTemplate.query("Select * from actor where contains (name, ?)",
                ((rs, rowNum) -> new Actor(rs.getInt("actor_id"), rs.getString("name"), rs.getString("year"))), name);
    }

    @Autowired
    FilmsRepository filmsRepository;

}
