package com.example.app.repository;
import com.example.app.model.film.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;


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
                    rs.getInt("filmId"),
                    rs.getString("tittle"),
                    rs.getDate("date")
                )
        );
    }

    public List<Film> find(List<Integer> ids) {
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return parameterJdbcTemplate.query("SELECT * FROM film WHERE filmId IN (:ids)",
                parameters,
                (rs, rowNum) -> new Film(
                        rs.getInt("filmId"),
                        rs.getString("tittle"),
                        rs.getDate("date")
                )
        );
    }

    public void add(Film film){
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO film(tittle, date) VALUES(?, ?)"
                    , Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, film.getTittle());
                ps.setDate(2, new Date(film.getDate().getTime()));
                return ps;
            }
        }, holder);
        int filmId = Objects.requireNonNull(holder.getKey()).intValue();
        addToDependentTable("filmgenre", filmId, film.getGenres());
        addToDependentTable("filmactor", filmId, film.getActors());
        addToDependentTable("filmdirector", filmId, film.getDirectors());
    }

    public void addToDependentTable(String table, int filmId, List<Integer> entityIds){
        jdbcTemplate.batchUpdate("INSERT INTO " + table + " VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, filmId);
                        ps.setInt(2, entityIds.get(i));
                    }
                    @Override
                    public int getBatchSize() {
                        return entityIds.size();
                    }
                });
    }

    public void delete(int filmId){
        jdbcTemplate.update(
                "DELETE FROM film WHERE filmId=?",
                filmId
        );
    }


    public void edit(Film film) {
        delete(film.getId());
        add(film);
    }

    @Override
    public int size() {
        return 0;
    }
}
