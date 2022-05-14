package com.app.repository;

import com.app.annotation.AddEntityHandler;
import com.app.model.genre.Genre;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

@SuppressWarnings("unused")
@Repository
public class GenresRepository extends AbstractRepository<Genre> {

    @Override
    public Genre findById(int id) {
        return null;
    }

    @Override
    public <S extends Genre> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Genre> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Genre> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Genre> findAll() {
        return null;
    }

    @Override
    public Iterable<Genre> findAllById(Iterable<Integer> integers) {
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
    public void delete(Genre entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Genre> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Genre> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Genre> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Genre> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Genre> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Genre> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Genre> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Genre> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Genre> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Genre> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Genre> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Genre, R> R findBy(Example<S> example,
                                         Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
