package com.example.app.repository;

import com.example.app.model.director.Director;
import com.example.app.model.film.Film;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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


}
