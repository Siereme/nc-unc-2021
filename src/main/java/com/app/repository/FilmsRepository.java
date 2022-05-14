package com.app.repository;

import com.app.annotation.AddEntityHandler;
import com.app.model.film.Film;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/** Film app.repository
 * @see Film
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
@SuppressWarnings("unused")
@Transactional
@Repository
public class FilmsRepository extends AbstractRepository<Film> {

    @Override
    public Film findById(int id) {
        return null;
    }

    @Override
    public <S extends Film> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Film> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Film> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Film> findAll() {
        return null;
    }

    @Override
    public Iterable<Film> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Film entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Film> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Film> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Film> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Film> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Film> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Film> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Film> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Film> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Film> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Film> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Film> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Film, R> R findBy(Example<S> example,
                                        Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
