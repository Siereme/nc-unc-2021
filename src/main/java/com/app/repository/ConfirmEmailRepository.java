package com.app.repository;

import com.app.model.confirmEmail.ConfirmEmail;
import com.app.model.director.Director;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class ConfirmEmailRepository extends AbstractRepository<ConfirmEmail>{

    @Override
    public <S extends ConfirmEmail> S save(S entity) {
        return null;
    }

    @Override
    public <S extends ConfirmEmail> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ConfirmEmail> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<ConfirmEmail> findAll() {
        return null;
    }

    @Override
    public Iterable<ConfirmEmail> findAllById(Iterable<Integer> integers) {
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
    public void delete(ConfirmEmail entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends ConfirmEmail> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ConfirmEmail> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ConfirmEmail> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ConfirmEmail> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends ConfirmEmail> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends ConfirmEmail> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ConfirmEmail> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ConfirmEmail> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ConfirmEmail> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ConfirmEmail> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ConfirmEmail> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ConfirmEmail, R> R findBy(Example<S> example,
                                                Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    public ConfirmEmail add(ConfirmEmail confirmEmail){
        try {
            entityManager.persist(confirmEmail);
            return confirmEmail;
        }catch (EntityExistsException ex){
            return null;
        }
    }

    @Override
    public ConfirmEmail findById(int id) {
        return null;
    }
}
