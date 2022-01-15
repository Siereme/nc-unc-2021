package com.example.app.repository;
import com.example.app.model.film.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


/** Film app.repository
 * @see Film
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
@Repository
public class FilmsRepository implements IRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Film> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM film",
                (rs, rowNum) -> new Film(
                    rs.getString("filmId"),
                    rs.getString("tittle"),
                    rs.getDate("date"))
                );
    }

    public void add(Film film){
        jdbcTemplate.update(
                "INSERT INTO film VALUES(?, ?, ?)",
                film.getId(),
                film.getTittle(),
                film.getDate()
        );
    }

    public void delete(String filmId){
        jdbcTemplate.update(
                "DELETE FROM film WHERE filmId = ?",
                filmId
        );
    }

    @Override
    public int size() {
        return 0;
    }
}
