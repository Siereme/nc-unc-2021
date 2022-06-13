package com.app.repository;

import com.app.annotation.AddEntityHandler;
import com.app.model.genre.Genre;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.math.BigInteger;
import java.util.*;

@SuppressWarnings("unused")
@Repository
public class GenresRepository extends AbstractRepository<Genre> {
    private static final Logger logger = Logger.getLogger(GenresRepository.class);

    public List<Genre> findAll() {
        return entityManager.createNamedQuery("Genre.findAllWithFilm", Genre.class).getResultList();
    }

    @Override
    public Genre findById(int id) {
        try{
            return entityManager.createNamedQuery("Genre.findById", Genre.class).setParameter("id", id).getSingleResult();
        }catch (NoResultException ex){
            return null;
        }
    }

    public List<Genre> find(List<Integer> ids) {
        if (ids != null && ids.size() < 1) {
            return Collections.emptyList();
        }

        return entityManager.createNamedQuery("Genre.findAllWithFilmByIds", Genre.class).setParameter("ids", ids).getResultList();

    }

    public List<Genre> findByTitles(List<String> titles) {
        if (titles == null || titles.size() < 1) {
            return new ArrayList<>();
        }

        return entityManager.createQuery("SELECT distinct g FROM Genre g left join fetch g.films WHERE g.tittle IN (:titles)",
                Genre.class).setParameter("titles", titles).getResultList();

    }

    @AddEntityHandler
    public Genre add(Genre genre) {
        try {
            entityManager.persist(genre);
            return genre;
        }catch (EntityExistsException ex){
            return null;
        }
    }

    @Override
    public void delete(int id) {
        Genre genre = entityManager.find(Genre.class, id);
        entityManager.remove(genre);
    }

    public void edit(Genre genre) {
        entityManager.merge(genre);
    }

    @Override
    public List<Genre> findByName(String name) {
        return entityManager.createQuery("Select distinct g from Genre g left join fetch g.films where g.tittle = :name", Genre.class)
                .setParameter("name", name).getResultList();
    }

    @SuppressWarnings("unused")
    @Override
    public int size() {
        BigInteger bigInteger =
                (BigInteger) entityManager.createNativeQuery("select count(*) from genre").getSingleResult();
        return bigInteger.intValue();
    }

    @Override
    public List<Genre> findByContains(String name) {
        return entityManager.createQuery(
                "select distinct g from Genre g left join fetch g.films where g.tittle like :name ESCAPE '!'",
                Genre.class).setParameter("name", '%' + name + '%').getResultList();
    }
}
