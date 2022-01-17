package com.example.app.repository;

import com.example.app.model.actor.Actor;
import com.example.app.model.director.Director;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DirectorsRepository extends AbstractRepository<Director> {
    @Override
    public List<Director> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM director",
                (rs, rowNum) -> new Director(
                        rs.getInt("directorId"),
                        rs.getString("name"),
                        rs.getString("year"))
        );
    }

    @Override
    public void add(Director entity) {
        jdbcTemplate.update(
                "INSERT INTO director(name, year) VALUES(?, ?)",
                entity.getName(),
                entity.getYear()
        );
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(
                "DELETE FROM director WHERE directorId=?",
                id
        );
    }

    @Override
    public void edit(Director entity) {
        jdbcTemplate.update(
                "UPDATE director SET name=?, year=? WHERE directorId=?",
                entity.getName(),
                entity.getYear(),
                entity.getId()
        );
    }

    @Override
    public List<Director> findByName(String name) {
/*        return jdbcTemplate.query("Select * from director where name = ?", rs -> {
            Map<Integer, Director> map = new HashMap<>();
            while (rs.next()){
                int id = rs.getInt("directorId");
                String directorName = rs.getString("name");
                String directorYear = rs.getString("year");
                Director director = new Director(id, directorName, directorYear);
                if(!map.containsKey(director.getId())){
                    map.put(director.getId(), director);
                }
            }
            return new ArrayList<>(map.values());
        }, name);*/

        return jdbcTemplate.query("Select * from director where name = ?",
                (rs, rowNum) -> new Director(rs.getInt("directorId"), rs.getString("name"), rs.getString("year")), name);

    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM director", Integer.class);
    }
}
