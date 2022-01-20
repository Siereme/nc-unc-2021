package com.example.app.repository;

import com.example.app.model.film.Film;
import com.example.app.model.genre.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenresRepository implements IRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Genre> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM genre",
                (rs, rowNum) -> new Genre(
                        rs.getInt("genreId"),
                        rs.getString("tittle")
                )
        );
    }

    public List<Genre> find(List<Integer> ids) {
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query("SELECT * FROM genre WHERE genreId IN (:ids)",
                parameters,
                (rs, rowNum) -> new Genre(
                        rs.getInt("genreId"),
                        rs.getString("tittle")
                )
        );
    }

    public List<Genre> findByFilms(List<Integer> ids){
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query(
                "SELECT genre.genreId, genre.tittle FROM genre " +
                        "INNER JOIN filmgenre ON genre.genreId=filmgenre.genreId " +
                        "WHERE filmgenre.filmId IN (:ids)",
                parameters,
                (rs, rowNum) -> new Genre(
                        rs.getInt("genreId"),
                        rs.getString("tittle")
                )
        );
    }

    public void add(Genre genre){
        jdbcTemplate.update(
                "INSERT INTO genre(tittle) VALUES(?)",
                genre.getTittle()
        );
    }

    public void delete(int genreId){
        jdbcTemplate.update(
                "DELETE FROM genre WHERE genreId=?",
                genreId
        );
    }

    public void edit(Genre genre) {
        jdbcTemplate.update(
                "UPDATE genre SET tittle=? WHERE genreId=?",
                genre.getTittle(),
                genre.getId()
        );
    }

    @Override
    public int size() {
        return 0;
    }
}
