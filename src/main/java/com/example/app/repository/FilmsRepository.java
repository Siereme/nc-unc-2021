package com.example.app.repository;

import com.example.app.model.film.Film;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Film app.repository
 * @see Film
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
@Repository
public class FilmsRepository extends AbstractRepository<Film> {

    public List<Film> findAll() {
        return jdbcTemplate.query("SELECT * FROM film",
                (rs, rowNum) -> new Film(rs.getInt("film_id"), rs.getString("tittle"), rs.getDate("date")));
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM film", Integer.class);
    }

    public void add(Film film) {
        jdbcTemplate.update("INSERT INTO film(tittle, date) VALUES(?, ?)", film.getTittle(), film.getDate());
    }

    public void delete(int filmId) {
        jdbcTemplate.update("DELETE FROM film WHERE film_id=?", filmId);
    }

    public void edit(Film film) {
        jdbcTemplate.update("UPDATE film SET tittle=? WHERE film_id=?", film.getTittle(), film.getId());
    }

    @Override
    public List<Film> findByName(String name) {

        return jdbcTemplate.query("Select * from film where tittle = ?",
                (rs, rowNum) -> new Film(rs.getInt("filmId"), rs.getString("tittle"), rs.getDate("date")), name);
    }

}
