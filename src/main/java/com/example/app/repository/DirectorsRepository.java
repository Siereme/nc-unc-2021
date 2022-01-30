package com.example.app.repository;

import com.example.app.model.director.Director;
import com.example.app.model.film.Film;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class DirectorsRepository extends AbstractRepository<Director> {

    @Autowired
    FilmsRepository filmsRepository;

    public List<Director> findAll() {
        return jdbcTemplate.query("SELECT * FROM director",
                (rs, rowNum) -> new Director(rs.getInt("director_id"), rs.getString("name"), rs.getString("year")));
    }

    public List<Director> find(List<Integer> ids) {
        if (ids.size() < 1) {
            return new ArrayList<>();
        }
        parameters.addValue("ids", ids);
        return parameterJdbcTemplate.query("SELECT * FROM director WHERE director_id IN (:ids)", parameters,
                (rs, rowNum) -> new Director(rs.getInt("director_id"), rs.getString("name"), rs.getString("year")));
    }

    public List<Director> findByFilms(List<Integer> ids) {
        parameters.addValue("ids", ids);
        return parameterJdbcTemplate.query("SELECT director.director_id, director.name, director.year FROM director "
                        + "INNER JOIN film_director ON director.director_id=film_director.director_id "
                        + "WHERE film_director.film_id IN (:ids)", parameters,
                (rs, rowNum) -> new Director(rs.getInt("director_id"), rs.getString("name"), rs.getString("year")));
    }

    public void add(Director director) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO director(name, year) VALUES(?, ?)", director.getName(), director.getYear(), holder);
        List<Integer> filmsId = director.getFilms();
        Integer directorId = Objects.requireNonNull(holder.getKey()).intValue();
        for (Integer filmId : filmsId) {
            jdbcTemplate.update("INSERT INTO film_director(film_id, director_id) values (?,?)", filmId, directorId);
        }
    }

    public void delete(int directorId) {
        jdbcTemplate.update("DELETE FROM director WHERE director_id=?", directorId);
    }

    public void edit(Director director) {
        String newName = director.getName();
        String newYear = director.getYear();
        Integer directorId = director.getId();
        jdbcTemplate.update("UPDATE director SET name=?, year = ? WHERE director_id=?", newName, newYear, directorId);

        List<Integer> filmListToDelete = filmsRepository.findFilmsToDelete(director, "film_director", "director_id");
        List<Integer> filmListToAdd = filmsRepository.findFilmsToAdd(director, "film_director", "director_id");
        for (Integer filmId : filmListToDelete) {
            jdbcTemplate.update("delete from film_director where director_id = ? and film_id = ?", directorId, filmId);
        }

        for (Integer filmId : filmListToAdd) {
            jdbcTemplate.update("insert into film_director(film_id, director_id) values (?,?)", filmId, directorId);
        }

    }

    @Override
    public List<Director> findByName(String name) {
        return jdbcTemplate.query("Select * from director where name = ?",
                ((rs, rowNum) -> new Director(rs.getInt("director_id"), rs.getString("name"), rs.getString("year"))),
                name);
    }

    public List<Film> findFilmsByDirectorId(Integer directorId) {
        return jdbcTemplate.query(
                "SELECT f.film_id as f_id, f.tittle as f_tittle, f.date as f_date FROM data_base.director d\n"
                        + "join film_director fa on fa.director_id = d.director_id\n"
                        + "join film f on f.film_id = fa.film_id\n" + "where d.director_id = ?",
                (rs, rowNum) -> new Film(rs.getInt("f_id"), rs.getString("f_tittle"), rs.getDate("f_date")),
                directorId);
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM director", Integer.class);
    }
}
