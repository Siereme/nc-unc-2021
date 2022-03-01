package com.app.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class AbstractRepository<T> implements IRepository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    public abstract T findById(int id);

}
