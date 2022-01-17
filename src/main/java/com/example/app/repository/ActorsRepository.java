package com.example.app.repository;

import com.example.app.model.actor.Actor;
import com.example.app.model.film.Film;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActorsRepository extends AbstractRepository<Actor> {

    @Override
    public List<Actor> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM actor",
                (rs, rowNum) -> new Actor(
                        rs.getInt("actorId"),
                        rs.getString("name"),
                        rs.getString("year"))
        );
    }

    @Override
    public void add(Actor entity) {
        jdbcTemplate.update(
                "INSERT INTO actor(name, year) VALUES(?, ?)",
                entity.getName(),
                entity.getYear()
        );
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(
                "DELETE FROM actor WHERE actorId=?",
                id
        );
    }

    @Override
    public void edit(Actor entity) {
        jdbcTemplate.update(
                "UPDATE film SET name=?, year=? WHERE filmId=?",
                entity.getName(),
                entity.getYear(),
                entity.getId()
        );
    }

    @Override
    public int size() {
        return 0;
    }
}
