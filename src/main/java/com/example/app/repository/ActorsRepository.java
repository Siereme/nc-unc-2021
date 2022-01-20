package com.example.app.repository;

import com.example.app.model.actor.Actor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActorsRepository extends AbstractRepository<Actor> {


    @Override
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

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM actor", Integer.class);
    }
}
