package com.app.repository;

import com.app.model.confirmEmail.ConfirmEmail;
import com.app.model.director.Director;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import java.util.List;

@Repository
public class ConfirmEmailRepository extends AbstractRepository<ConfirmEmail>{

    @Override
    public List<ConfirmEmail> findAll() {
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
