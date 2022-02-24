package com.app.repository;

import com.app.model.film.Film;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
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
        return entityManager.createNamedQuery("Film.findAllWithAllByIds")
                .setParameter("ids", ids)
                .getResultList();
    }

    public void add(Film film) {
        entityManager.persist(film);
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
        return entityManager.createQuery("select distinct f from Film f where f.tittle =:name").setParameter("name", name)
                .getResultList();
    }

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
