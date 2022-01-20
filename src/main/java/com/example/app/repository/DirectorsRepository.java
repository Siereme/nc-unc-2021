package com.example.app.repository;

import com.example.app.model.director.Director;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DirectorsRepository extends AbstractRepository<Director> {
    @Override
    public List<Director> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM director",
                (rs, rowNum) -> new Director(
                        rs.getInt("director_id"),
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
                "DELETE FROM director WHERE director_id=?",
                id
        );
    }

    @Override
    public void edit(Director entity) {
        jdbcTemplate.update(
                "UPDATE director SET name=?, year=? WHERE director_id=?",
                entity.getName(),
                entity.getYear(),
                entity.getId()
        );
    }

    @Override
    public List<Director> findByName(String name) {

        return jdbcTemplate.query("Select * from director where name = ?",
                (rs, rowNum) -> new Director(rs.getInt("directorId"), rs.getString("name"), rs.getString("year")), name);

    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM director", Integer.class);
    }
}
