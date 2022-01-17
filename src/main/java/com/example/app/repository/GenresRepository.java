package com.example.app.repository;

import com.example.app.model.film.Film;
import com.example.app.model.genre.Genre;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class GenresRepository extends AbstractRepository<Genre> {
    @Override
    public List<Genre> findAll() {
        return jdbcTemplate.query("SELECT * FROM genre",
                (rs, rowNum) -> new Genre(rs.getInt("genreId"), rs.getString("tittle")));
    }

    @Override
    public void add(Genre entity) {
        jdbcTemplate.update("INSERT INTO genre(tittle) VALUES(?)", entity.getTittle());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM genre WHERE filmId=?", id);
    }

    @Override
    public void edit(Genre entity) {
        jdbcTemplate.update("UPDATE film SET tittle=? WHERE genreId=?", entity.getTittle(), entity.getId());
    }

    @Override
    public int size() {
        return 0;
    }
}
