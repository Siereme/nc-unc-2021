package com.app.repository;

import com.app.model.director.Director;
import com.app.model.film.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class DirectorsRepository extends AbstractRepository<Director> {

    @Autowired
    FilmsRepository filmsRepository;

    public List<Director> findAll() {
        return entityManager.createNamedQuery("Director.findAllWithFilm", Director.class).getResultList();
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
/*        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO director(name, year) VALUES(?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, director.getName());
                ps.setString(2, director.getYear());
                return ps;
            }
        }, holder);
        List<Integer> filmsId = director.getFilms();
        Integer directorId = Objects.requireNonNull(holder.getKey()).intValue();
        for (Integer filmId : filmsId) {
            jdbcTemplate.update("INSERT INTO film_director(film_id, director_id) values (?,?)", filmId, directorId);
        }*/
    }

    public void delete(int directorId) {
        Director director = entityManager.find(Director.class, directorId);
        entityManager.getTransaction().begin();
        entityManager.remove(director);
        entityManager.getTransaction().commit();
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
        return entityManager.createNativeQuery(
                        "SELECT f.film_id as film_id, f.tittle as tittle, f.date as date FROM data_base.director d\n"
                                + "join film_director fa on fa.director_id = d.director_id\n"
                                + "join film f on f.film_id = fa.film_id\n" + "where d.director_id = :directorId", Film.class)
                .setParameter("directorId", directorId).getResultList();
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM director", Integer.class);
    }

    @Override
    public List<Director> findByContains(String name) {
        return parameterJdbcTemplate.query("Select * from director where name like :name ESCAPE '!'",
                Collections.singletonMap("name", '%' + name + '%'),
                (rs, rowNum) -> new Director(rs.getInt("director_id"), rs.getString("name"), rs.getString("year")));
    }
}
