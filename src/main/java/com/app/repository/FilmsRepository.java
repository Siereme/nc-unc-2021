package com.app.repository;

import com.app.model.IParticipatesFilm;
import com.app.model.film.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Film app.repository
 * @see Film
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
@Transactional
@Repository
public class FilmsRepository extends AbstractRepository<Film> {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    public List<Film> findAll() {
        return entityManager.createNamedQuery("Film.findAllWithAll", Film.class).getResultList();
    }

    public Film findById(int id) {
        return entityManager.createNamedQuery("Film.findById", Film.class).setParameter("id", id).getSingleResult();
    }

    public List<Film> find(List<Integer> ids) {
        if (ids != null && ids.size() < 1) {
            return Collections.emptyList();
        }

/*        parameters.addValue("ids", ids);
        return parameterJdbcTemplate.query("SELECT film.film_id, film.tittle, film.date, "
                + "film_genre.genre_id, film_actor.actor_id, film_director.director_id "
                + "FROM film "
                + "LEFT JOIN film_genre ON film.film_id=film_genre.film_id "
                + "LEFT JOIN film_actor ON film.film_id=film_actor.film_id "
                + "LEFT JOIN film_director ON film.film_id=film_director.film_id "
                + "WHERE film.film_id IN (:ids) OR COALESCE(:ids) IS NULL",
                parameters, new QueryRowMapper());*/

        return null;
    }

    private final static class QueryRowMapper implements ResultSetExtractor<List<Film>> {
        @Override
        public List<Film> extractData(ResultSet rs) throws SQLException, DataAccessException {
           /* Map<Integer, Film> filmMap = new HashMap<>();
            Film film;
            while (rs.next()){
                int id = rs.getInt("film.film_id");
                film = filmMap.get(id);
                if(film == null){
                    film = new Film();
                    film.setId(id);
                    film.setTittle(rs.getString("film.tittle"));
                    film.setDate(rs.getDate("film.date"));
                    film.setGenres(new ArrayList<>());
                    film.setActors(new ArrayList<>());
                    film.setDirectors(new ArrayList<>());
                    filmMap.put(id, film);
                }
                int genreId = rs.getInt("film_genre.genre_id");
                boolean hasGenre = film.getGenres().stream().anyMatch(filmGenreId -> filmGenreId == genreId);
                if(genreId > 0 && !hasGenre){
                    film.addGenre(genreId);
                }
                int actorId = rs.getInt("film_actor.actor_id");
                boolean hasActor = film.getActors().stream().anyMatch(filmActorId -> filmActorId == actorId);
                if(actorId > 0 && !hasActor){
                    film.addActor(actorId);
                }
                int directorId = rs.getInt("film_director.director_id");
                boolean hasDirector = film.getDirectors().stream().anyMatch(filmDirectorId -> filmDirectorId == directorId);
                if(directorId > 0 && !hasDirector){
                    film.addDirector(directorId);
                }
            }
            return new ArrayList<>(filmMap.values());*/
            return null;
        }
    }

    public List<Film> findByTitles(List<String> titles) {
        if (titles == null || titles.size() < 1) {
            return new ArrayList<>();
        }

        parameters.addValue("titles", titles);
        List<Integer> filmIds =
                parameterJdbcTemplate.query("SELECT film_id FROM film WHERE tittle IN (:titles)", parameters,
                        (rs, rowNum) -> rs.getInt("film_id"));
        return find(filmIds);
    }

    public List<Film> findByGenres(List<Integer> ids) {
    /*    if (ids == null || ids.size() < 1) {
            return new ArrayList<>();
        }

        parameters.addValue("ids", ids);
        return parameterJdbcTemplate.query("SELECT film.film_id, film.tittle FROM film "
                        + "INNER JOIN film_genre ON film.film_id=film_genre.film_id " + "WHERE film_genre.genre_id IN (:ids)",
                parameters, (rs, rowNum) -> new Film(rs.getInt("film.film_id"), rs.getString("film.tittle")));*/
        return null;
    }

    public void add(Film film) {
        entityManager.persist(film);
/*        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO film(tittle, date) VALUES(?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, film.getTittle());
                ps.setDate(2, new Date(film.getDate().getTime()));
                return ps;
            }
        }, holder);
        int filmId = Objects.requireNonNull(holder.getKey()).intValue();
        addEntitiesIds("genre", filmId, film.getGenres());
        addEntitiesIds("actor", filmId, film.getActors());
        addEntitiesIds("director", filmId, film.getDirectors());*/
    }

    private void addEntitiesIds(String entity, int filmId, List<Integer> entityIds) {
        if (entityIds == null || entityIds.size() < 1) {
            return;
        }
        String query = String.format("INSERT INTO film_%s (film_id, %s_id) VALUES (?, ?)", entity, entity);
        jdbcTemplate.batchUpdate(query, new BatchEntitiesSetter(filmId, entityIds));
    }

    private void deleteEntitiesIds(String entity, int filmId, List<Integer> entityIds) {
        if (entityIds == null || entityIds.size() < 1) {
            return;
        }
        String query = String.format("DELETE FROM film_%s WHERE film_id=? AND %s_id=?", entity, entity);
        jdbcTemplate.batchUpdate(query, new BatchEntitiesSetter(filmId, entityIds));
    }

    private final static class BatchEntitiesSetter implements BatchPreparedStatementSetter {

        private final int filmId;
        private final List<Integer> entityIds;

        public BatchEntitiesSetter(int filmId, List<Integer> entityIds) {
            this.filmId = filmId;
            this.entityIds = entityIds;
        }

        @Override
        public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, filmId);
            ps.setInt(2, entityIds.get(i));
        }

        @Override
        public int getBatchSize() {
            return entityIds.size();
        }
    }

    public List<Integer> getEntitiesIds(String entity, int filmId) {
        SqlParameterSource parameter = new MapSqlParameterSource("id", filmId);
        String query = String.format("SELECT %s_id FROM film_%s WHERE film_id=:id", entity, entity);
        return parameterJdbcTemplate.queryForList(query, parameter, Integer.class);
    }

    private List<Integer> getEditEntitiesIds(List<Integer> editIds, List<Integer> ids) {
        List<Integer> editList = new ArrayList<>();
        for (int id : ids) {
            boolean isContains = editIds.stream().anyMatch(editId -> id == editId);
            if (!isContains) {
                editList.add(id);
            }
        }
        return editList;
    }

    public void edit(Film film) {
        entityManager.merge(film);
      /*  jdbcTemplate.update("UPDATE film set tittle=?, date=? WHERE film_id=?", film.getTittle(), film.getDate(), film.getId());

        List<Integer> genres = getEntitiesIds("genre", film.getId());
        List<Integer> actors = getEntitiesIds("actor", film.getId());
        List<Integer> directors = getEntitiesIds("director", film.getId());

        List<Integer> addGenres = getEditEntitiesIds(genres, film.getGenres());
        List<Integer> addActors = getEditEntitiesIds(actors, film.getActors());
        List<Integer> addDirector = getEditEntitiesIds(directors, film.getDirectors());

        List<Integer> removeGenres = getEditEntitiesIds(film.getGenres(), genres);
        List<Integer> removeActors = getEditEntitiesIds(film.getActors(), actors);
        List<Integer> removeDirectors = getEditEntitiesIds(film.getDirectors(), directors);


        deleteEntitiesIds("genre", film.getId(), removeGenres);
        deleteEntitiesIds("actor", film.getId(), removeActors);
        deleteEntitiesIds("director", film.getId(), removeDirectors);

        addEntitiesIds("genre", film.getId(), addGenres);
        addEntitiesIds("actor", film.getId(), addActors);
        addEntitiesIds("director", film.getId(), addDirector);*/
    }

    public void delete(int filmId) {
        Film film = entityManager.find(Film.class, filmId);
        entityManager.getTransaction().begin();
        entityManager.remove(film);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Film> findByName(String name) {
/*        return jdbcTemplate.query("Select * from film where tittle = ?",
                ((rs, rowNum) -> new Film(rs.getInt("film_id"), rs.getString("tittle"), rs.getDate("date"))));*/
        return null;
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM film", Integer.class);
    }

    @Override
    public List<Film> findByContains(String name) {
/*        return parameterJdbcTemplate.query("Select * from film where film.tittle like :name ESCAPE '!'",
                Collections.singletonMap("name", '%'+name + '%'),
                (rs, rowNum) -> new Film(rs.getInt("film_id"),
                        rs.getString("tittle"),
                        rs.getDate("date")));*/
        return null;
    }

    /**
     * the function finds the movies that will need to be deleted from entity
     * @param entity - Actor or Director, from whom it is necessary to delete movies
     * @param filmEntityTable - name of the table from database in which movies will be deleted
     * */
    public List<Integer> findFilmsToDelete(IParticipatesFilm entity, String filmEntityTable, String entityColName) {
/*        List<Integer> newFilmsId = entity.getFilms(); // list of new films
        String sql = "Select * from " + filmEntityTable + " where " + entityColName + " = ?";
        List<Integer> oldFilmsId =
                jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("film_id"), entity.getId()); // old list of films

        oldFilmsId.removeIf(oldId -> newFilmsId.contains(oldId));

        return oldFilmsId;*/
        return null;
    }

    public List<Integer> findFilmsToAdd(IParticipatesFilm entity, String filmEntityTable, String entityColName) {
/*        List<Integer> newFilmsId = entity.getFilms(); // list of new films
        String sql = "Select * from " + filmEntityTable + " where " + entityColName + " = ?";
        List<Integer> oldFilmsId =
                jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("film_id"), entity.getId()); // old list of films

        List<Integer> resultList = new LinkedList<>();

        for (Integer newFilmId : newFilmsId) {
            if (!oldFilmsId.contains(newFilmId)) {
                resultList.add(newFilmId);
            }
        }

        return resultList;*/
        return null;
    }
}
