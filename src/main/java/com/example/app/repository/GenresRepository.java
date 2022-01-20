package com.example.app.repository;

import com.example.app.model.genre.Genre;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

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
    public List<Genre> findByName(String name) {
/*        return jdbcTemplate.query("Select * from genre where name = ?", rs -> {
            Map<Integer, Genre> map = new HashMap<>();
            while (rs.next()) {
                int id = rs.getInt("genreId");
                String tittle = rs.getString("tittle");
                Genre genre = new Genre(id, tittle);
                if (!map.containsKey(genre.getId())) {
                    map.put(genre.getId(), genre);
                }
            }
            return new ArrayList<>(map.values());
        }, name);*/

        return jdbcTemplate.query("Select * from genre where name = ?",
                (rs, rowNum) -> new Genre(rs.getInt("genreId"), rs.getString("tittle")), name);
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM genre", Integer.class);
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
}
