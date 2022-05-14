package com.app.repository;

import com.app.model.IEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class AbstractRepository<T> implements MongoRepository<T, Integer> {

    @PersistenceContext
    protected EntityManager entityManager;

    public abstract T findById(int id);

}
