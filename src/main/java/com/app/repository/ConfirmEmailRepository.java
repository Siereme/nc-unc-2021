package com.app.repository;

import com.app.model.confirmEmail.ConfirmEmail;
import com.app.model.director.Director;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConfirmEmailRepository extends AbstractRepository<ConfirmEmail>{

    @Override
    public List<ConfirmEmail> findAll() {
        return null;
    }

    public void add(ConfirmEmail confirmEmail){
        entityManager.persist(confirmEmail);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void edit(ConfirmEmail entity) {

    }

    @Override
    public List<ConfirmEmail> findByName(String name) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public List<ConfirmEmail> findByContains(String name) {
        return null;
    }

    @Override
    public ConfirmEmail findById(int id) {
        return null;
    }
}
