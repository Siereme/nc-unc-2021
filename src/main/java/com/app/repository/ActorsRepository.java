package com.app.repository;

import com.app.model.actor.Actor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings("unused")
@Repository
@Transactional
public class ActorsRepository extends AbstractRepository<Actor> {

    @Override
    public Actor findById(int id) {
        return mongoTemplate.findById(id, Actor.class);
    }

    @Override
    public <S extends Actor> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Actor> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Actor> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Actor> findAll() {
        return null;
    }

    @Override
    public Iterable<Actor> findAllById(Iterable<Integer> integers) {
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
    public void delete(Actor entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Actor> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Actor> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Actor> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Actor> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Actor> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Actor> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Actor> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Actor> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Actor> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Actor> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Actor> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Actor, R> R findBy(Example<S> example,
                                         Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
