package com.app.repository;

import com.app.annotation.AddEntityHandler;
import com.app.model.film.Film;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

/** Film app.repository
 * @see Film
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
@SuppressWarnings("unused")
@Transactional
@Repository
public class FilmsRepository extends AbstractRepository<Film> {

    public List<Film> findAll() {
        return entityManager.createNamedQuery("Film.findAllWithAll", Film.class).getResultList();
    }

    public Film findById(int id) {
        try{
            return entityManager.createNamedQuery("Film.findById", Film.class).setParameter("id", id).getSingleResult();
        }catch (NoResultException ex){
            return null;
        }
    }

    public List<Film> find(List<Integer> ids) {
        if (ids != null && ids.size() < 1) {
            return Collections.emptyList();
        }
        return entityManager.createNamedQuery("Film.findAllWithAllByIds", Film.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    @AddEntityHandler
    public Film add(Film film) {
        try {
            entityManager.persist(film);
            return film;
        }catch (EntityExistsException ex){
            return null;
        }
    }

    @Override
    public void delete(int id) {
        Film film = entityManager.find(Film.class, id);
        entityManager.remove(film);
    }


    public void edit(Film film) {
        entityManager.merge(film);
    }

    @Override
    public List<Film> findByName(String name) {
        return entityManager.createQuery("select distinct f from Film f left join fetch f.genres left join fetch f.actors left join fetch f.directors "
                        + "where f.tittle =:name", Film.class).setParameter("name", name)
                .getResultList();
    }

    @SuppressWarnings("unused")
    @Override
    public int size() {
        BigInteger bigInteger =
                (BigInteger) entityManager.createNativeQuery("select count(*) from film").getSingleResult();
        return bigInteger.intValue();
    }

    @Override
    public List<Film> findByContains(String name) {
        return entityManager.createQuery("select distinct f from Film f "
                        + "left join fetch f.actors "
                        + "left join fetch f.genres "
                        + "left join fetch f.directors "
                        + "where f.tittle like :name ESCAPE '!'",
                Film.class).setParameter("name", '%' + name + '%').getResultList();
    }

}
