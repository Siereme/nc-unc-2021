package com.example.app.repository;

import com.example.app.model.director.Director;
import com.example.app.model.film.Film;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Film app.repository
 * @see Film
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
@Repository
public class FilmsRepository extends AbstractRepository<Film> {

    public List<Film> findAll() {
        return jdbcTemplate.query("SELECT * FROM film",
                (rs, rowNum) -> new Film(rs.getInt("filmId"), rs.getString("tittle"), rs.getDate("date")));
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM film", Integer.class);
    }

    public void add(Film film) {
        jdbcTemplate.update("INSERT INTO film(tittle, date) VALUES(?, ?)", film.getTittle(), film.getDate());
    }

    public void delete(int filmId) {
        jdbcTemplate.update("DELETE FROM film WHERE filmId=?", filmId);
    }

    public void edit(Film film) {
        jdbcTemplate.update("UPDATE film SET tittle=? WHERE filmId=?", film.getTittle(), film.getId());
    }

    @Override
    public List<Film> findByName(String name) {
/*        return jdbcTemplate.query("Select * from film where name = ?", rs -> {
            Map<Integer, Film> map = new HashMap<>();
            while (rs.next()) {
                int id = rs.getInt("filmId");
                String tittle = rs.getString("tittle");
                Date date = rs.getDate("date");
                Film film = new Film(id, tittle, date);
                if (!map.containsKey(film.getId())) {
                    map.put(film.getId(), film);
                }
            }
            return new ArrayList<>(map.values());
        }, name);*/

        return jdbcTemplate.query("Select * from film where name = ?",
                (rs, rowNum) -> new Film(rs.getInt("filmId"), rs.getString("tittle"), rs.getDate("date")), name);
    }

}
