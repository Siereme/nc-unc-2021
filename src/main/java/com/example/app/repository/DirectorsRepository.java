package com.example.app.repository;

import com.example.app.model.actor.Actor;
import com.example.app.model.director.Director;
import com.example.app.model.genre.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DirectorsRepository implements IRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Director> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM director",
                (rs, rowNum) -> new Director(
                        rs.getInt("directorId"),
                        rs.getString("name"),
                        rs.getString("year")
                )
        );
    }

    public List<Director> find(List<Integer> ids) {
        if(ids.size() < 1) return new ArrayList<>();
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query("SELECT * FROM director WHERE directorId IN (:ids)",
                parameters,
                (rs, rowNum) -> new Director(
                        rs.getInt("directorId"),
                        rs.getString("name"),
                        rs.getString("year")
                )
        );
    }

    public List<Director> findByFilms(List<Integer> ids){
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query(
                "SELECT director.directorId, director.name, director.year FROM director " +
                        "INNER JOIN filmdirector ON director.directorId=filmdirector.directorId " +
                        "WHERE filmdirector.filmId IN (:ids)",
                parameters,
                (rs, rowNum) -> new Director(
                        rs.getInt("directorId"),
                        rs.getString("name"),
                        rs.getString("year")
                )
        );
    }

    public void add(Director director){
        jdbcTemplate.update(
                "INSERT INTO director(name, year) VALUES(?, ?)",
                director.getName(),
                director.getYear()
        );
    }

    public void delete(int directorId){
        jdbcTemplate.update(
                "DELETE FROM director WHERE directorId=?",
                directorId
        );
    }

    public void edit(Director director) {
        jdbcTemplate.update(
                "UPDATE director SET name=? WHERE directorId=?",
                director.getName(),
                director.getId()
        );
    }

    @Override
    public int size() {
        return 0;
    }
}
