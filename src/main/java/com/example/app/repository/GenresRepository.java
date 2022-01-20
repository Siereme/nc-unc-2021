package com.example.app.repository;

import com.example.app.model.genre.Genre;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenresRepository extends AbstractRepository<Genre> {
    @Override
    public List<Genre> findAll() {
        return jdbcTemplate.query("SELECT * FROM genre",
                (rs, rowNum) -> new Genre(rs.getInt("genre_id"), rs.getString("tittle")));
    }

    @Override
    public void add(Genre entity) {
        jdbcTemplate.update("INSERT INTO genre(tittle) VALUES(?)", entity.getTittle());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM genre WHERE genre_id=?", id);
    }

    @Override
    public void edit(Genre entity) {
        jdbcTemplate.update("UPDATE genre SET tittle=? WHERE genre_id=?", entity.getTittle(), entity.getId());
    }

    @Override
    public List<Genre> findByName(String name) {

        return jdbcTemplate.query("Select * from genre where tittle = ?",
                (rs, rowNum) -> new Genre(rs.getInt("genreId"), rs.getString("tittle")), name);
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM genre", Integer.class);
    }
}
